package kg.pm.patientservice.application

import kg.pm.patientservice.application.dto.patient.PatientResult
import kg.pm.patientservice.application.dto.patient.PatientSearchCriteria
import kg.pm.patientservice.application.mapper.PatientMapper
import kg.pm.patientservice.domain.core.repository.PatientRetrieverRepository
import kg.pm.patientservice.shared.PageResult
import kg.pm.patientservice.shared.transform
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Application service that orchestrates domain operations
 * and maps domain entities to application DTOs.
 */
interface PatientSearchService {
    fun getPatients(patientSearchCriteria: PatientSearchCriteria): PageResult<PatientResult>
}

@Service
class PatientSearchServiceImpl(
    private val patientMapper: PatientMapper,
    private val patientRetrieverRepository: PatientRetrieverRepository
) : PatientSearchService {

    @Transactional
    override fun getPatients(patientSearchCriteria: PatientSearchCriteria): PageResult<PatientResult> {
        return patientRetrieverRepository
            .search(patientSearchCriteria)
            .transform { patientMapper.toPatientResult(it) }
    }
}