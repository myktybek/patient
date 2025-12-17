package kg.pm.patientservice.application.usecase

import kg.pm.patientservice.domain.core.event.DomainEventPublisher
import kg.pm.patientservice.domain.core.model.valueobject.PatientId
import kg.pm.patientservice.domain.core.repository.PatientRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use case for deleting a patient.
 * Marks the patient for deletion, publishes domain events, and removes from repository.
 */
@Service
class DeletePatientUseCase(
    private val patientRepository: PatientRepository,
    private val eventPublisher: DomainEventPublisher
) {

    @Transactional
    fun execute(patientId: Long) {
        val id = PatientId(patientId)

        // Load patient
        val patient = patientRepository.findById(id)
            ?: throw PatientNotFoundException("Patient with ID $patientId not found")

        // Mark for deletion (publishes PatientDeletedEvent)
        patient.markForDeletion()

        // Publish domain events before deletion
        eventPublisher.publish(patient.domainEvents)

        // Delete from repository
        patientRepository.deleteById(id)
    }
}
