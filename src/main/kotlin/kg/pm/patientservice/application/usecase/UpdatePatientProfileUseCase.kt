package kg.pm.patientservice.application.usecase

import kg.pm.patientservice.application.dto.patient.AddressCommand
import kg.pm.patientservice.application.dto.patient.UpdatePatientProfileCommand
import kg.pm.patientservice.domain.core.event.DomainEventPublisher
import kg.pm.patientservice.domain.core.model.valueobject.Address
import kg.pm.patientservice.domain.core.model.valueobject.PatientId
import kg.pm.patientservice.domain.core.repository.PatientRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use case for updating a patient's profile information.
 * Loads the patient, updates their profile, persists changes, and publishes domain events.
 */
@Service
class UpdatePatientProfileUseCase(
    private val patientRepository: PatientRepository,
    private val eventPublisher: DomainEventPublisher
) {

    @Transactional
    fun execute(command: UpdatePatientProfileCommand) {
        val patientId = PatientId(command.patientId)

        // Load patient
        val patient = patientRepository.findById(patientId)
            ?: throw PatientNotFoundException("Patient with ID ${command.patientId} not found")

        // Convert command to domain value objects
        val name = command.name
        val address = command.address?.toDomain()

        // Update profile using domain method
        patient.updateProfile(
            name = name,
            address = address,
            dateOfBirth = command.dateOfBirth
        )

        // Save changes
        patientRepository.save(patient)

        // Publish domain events
        eventPublisher.publish(patient.domainEvents)
    }
}

/**
 * Extension function to convert AddressCommand to domain Address value object.
 */
private fun AddressCommand.toDomain(): Address {
    return Address(
        street = this.street,
        city = this.city,
        state = this.state,
        postalCode = this.postalCode,
        country = this.country
    )
}

/**
 * Exception thrown when a patient cannot be found.
 */
class PatientNotFoundException(message: String) : IllegalArgumentException(message)
