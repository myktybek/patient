package kg.pm.patientservice.application.usecase

import kg.pm.patientservice.application.command.AddressCommand
import kg.pm.patientservice.application.command.RegisterPatientCommand
import kg.pm.patientservice.domain.core.event.DomainEventPublisher
import kg.pm.patientservice.domain.core.model.Patient
import kg.pm.patientservice.domain.core.model.valueobject.Address
import kg.pm.patientservice.domain.core.model.valueobject.Email
import kg.pm.patientservice.domain.core.model.valueobject.Name
import kg.pm.patientservice.domain.core.model.valueobject.PatientId
import kg.pm.patientservice.domain.core.repository.PatientRepository
import kg.pm.patientservice.domain.core.service.PatientService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use case for registering a new patient.
 * Validates email uniqueness, creates the patient aggregate, persists it, and publishes domain events.
 */
@Service
class RegisterPatientUseCase(
    private val patientRepository: PatientRepository,
    private val patientService: PatientService,
    private val eventPublisher: DomainEventPublisher
) {

    @Transactional
    fun execute(command: RegisterPatientCommand): PatientId {
        // Convert command to domain value objects
        val email = Email(command.email)
        val name = command.name?.let { Name(it) }
        val address = command.address?.toDomain()

        patientService.ensureEmailIsUnique(email)

        // Create patient using factory method
        val patient = Patient.register(
            email = email,
            name = name,
            address = address,
            dateOfBirth = command.dateOfBirth
        )

        // Save patient
        val savedPatient = patientRepository.save(patient)

        // Publish domain events
        eventPublisher.publish(savedPatient.domainEvents)

        return savedPatient.id!!
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
