package kg.pm.patientservice.domain.core.repository

import kg.pm.patientservice.application.dto.patient.PatientSearchCriteria
import kg.pm.patientservice.domain.core.model.Patient
import kg.pm.patientservice.shared.PageResult

/**
 * Application layer repository interface.
 * Infrastructure layer will implement this interface.
 * This allows infrastructure to depend on application DTOs (pragmatic approach).
 */
interface PatientRetrieverRepository {
    fun search(searchCriteria: PatientSearchCriteria): PageResult<Patient>
}