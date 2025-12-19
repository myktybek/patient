package kg.pm.patientservice.application.usecase

import kg.pm.patientservice.application.dto.patient.AddressCommand
import kg.pm.patientservice.application.dto.patient.RegisterPatientCommand
import kg.pm.patientservice.domain.core.event.DomainEventPublisher
import kg.pm.patientservice.domain.core.model.Patient
import kg.pm.patientservice.domain.core.model.valueobject.Address
import kg.pm.patientservice.domain.core.model.valueobject.Email
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
    fun execute(command: RegisterPatientCommand): Long {
        val email = Email(command.email)
        val address = command.address?.toDomain()

        patientService.ensureEmailIsUnique(email)

        val patient = Patient.register(
            email = email,
            name = command.name,
            address = address,
            dateOfBirth = command.dateOfBirth
        )
        val savedPatient: Patient = patientRepository.save(patient)

        // Publish domain events
        eventPublisher.publish(savedPatient.domainEvents)

        return savedPatient.id!!.value
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
