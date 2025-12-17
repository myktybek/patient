package kg.pm.patientservice.application.usecase

import kg.pm.patientservice.application.command.ChangePatientEmailCommand
import kg.pm.patientservice.domain.core.event.DomainEventPublisher
import kg.pm.patientservice.domain.core.model.valueobject.Email
import kg.pm.patientservice.domain.core.model.valueobject.PatientId
import kg.pm.patientservice.domain.core.repository.PatientRepository
import kg.pm.patientservice.domain.core.service.PatientService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use case for changing a patient's email address.
 * Validates email uniqueness, updates the email, persists changes, and publishes domain events.
 */
@Service
class ChangePatientEmailUseCase(
    private val patientRepository: PatientRepository,
    private val patientService: PatientService,
    private val eventPublisher: DomainEventPublisher
) {

    @Transactional
    fun execute(command: ChangePatientEmailCommand) {
        val patientId = PatientId(command.patientId)
        val newEmail = Email(command.newEmail)

        val patient = patientRepository.findById(patientId)
            ?: throw PatientNotFoundException("Patient with ID ${command.patientId} not found")

        patientService.ensureEmailIsUnique(newEmail, excludePatientId = patientId)

        patient.changeEmail(newEmail)

        // Save changes
        patientRepository.save(patient)

        // Publish domain events
        eventPublisher.publish(patient.domainEvents)
    }
}
