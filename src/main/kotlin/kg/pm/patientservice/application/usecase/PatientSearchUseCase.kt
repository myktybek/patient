package kg.pm.patientservice.application.usecase

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
interface PatientSearchUseCase {
    fun search(patientSearchCriteria: PatientSearchCriteria): PageResult<PatientResult>
}

@Service
class PatientSearchUseCaseImpl(
    private val patientMapper: PatientMapper,
    private val patientRetrieverRepository: PatientRetrieverRepository
) : PatientSearchUseCase {

    @Transactional
    override fun search(patientSearchCriteria: PatientSearchCriteria): PageResult<PatientResult> {
        return patientRetrieverRepository
            .search(patientSearchCriteria)
            .transform { patientMapper.toPatientResult(it) }
    }
}