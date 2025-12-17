package kg.pm.patientservice.domain.core.service

import kg.pm.patientservice.domain.core.model.valueobject.Email
import kg.pm.patientservice.domain.core.model.valueobject.PatientId
import kg.pm.patientservice.domain.core.repository.PatientRepository
import org.springframework.stereotype.Service

/**
 * Domain service for Patient aggregate.
 * Handles business logic that doesn't naturally fit within the Patient entity.
 */
interface PatientService {
    /**
     * Validates that an email is unique in the system.
     * Optionally excludes a specific patient ID from the check (useful for email changes).
     *
     * @throws EmailNotUniqueException if the email is already taken
     */
    fun ensureEmailIsUnique(email: Email, excludePatientId: PatientId? = null)

    /**
     * Checks if an email is unique without throwing an exception.
     * Optionally excludes a specific patient ID from the check.
     */
    fun isEmailUnique(email: Email, excludePatientId: PatientId? = null): Boolean
}

@Service
class PatientServiceImpl(
    private val patientRepository: PatientRepository
) : PatientService {

    override fun ensureEmailIsUnique(email: Email, excludePatientId: PatientId?) {
        if (!isEmailUnique(email, excludePatientId)) {
            throw EmailNotUniqueException("Email ${email.value} is already registered to another patient")
        }
    }

    override fun isEmailUnique(email: Email, excludePatientId: PatientId?): Boolean {
        return !patientRepository.existsByEmail(email, excludePatientId)
    }
}

/**
 * Exception thrown when attempting to use an email that's already registered.
 */
class EmailNotUniqueException(message: String) : IllegalStateException(message)
