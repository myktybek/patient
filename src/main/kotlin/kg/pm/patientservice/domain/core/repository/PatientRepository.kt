package kg.pm.patientservice.domain.core.repository

import kg.pm.patientservice.domain.core.model.Patient
import kg.pm.patientservice.domain.core.model.valueobject.Email
import kg.pm.patientservice.domain.core.model.valueobject.PatientId

/**
 * Repository interface for Patient aggregate write operations.
 * Defined in the domain layer, implemented in the infrastructure layer.
 */
interface PatientRepository {
    /**
     * Saves a patient (insert or update).
     * Returns the saved patient with populated ID if it was newly created.
     */
    fun save(patient: Patient): Patient

    /**
     * Finds a patient by their ID.
     * Returns null if not found.
     */
    fun findById(id: PatientId): Patient?

    /**
     * Finds a patient by their email address.
     * Returns null if not found.
     */
    fun findByEmail(email: Email): Patient?

    /**
     * Checks if a patient with the given email exists.
     * Optionally excludes a specific patient ID from the check (useful for updates).
     */
    fun existsByEmail(email: Email, excludePatientId: PatientId? = null): Boolean

    /**
     * Deletes a patient by their ID.
     */
    fun deleteById(id: PatientId)
}
