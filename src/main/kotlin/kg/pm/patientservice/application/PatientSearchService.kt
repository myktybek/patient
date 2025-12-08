package kg.pm.patientservice.application

import jakarta.transaction.Transactional
import kg.pm.patientservice.application.dto.PatientResult
import kg.pm.patientservice.domain.core.service.PatientService
import kg.pm.patientservice.domain.dto.PatientSearchCriteria
import kg.pm.patientservice.shared.PageResult
import org.springframework.stereotype.Service

interface PatientSearchService {
    fun getPatients(patientSearchCriteria: PatientSearchCriteria): PageResult<PatientResult>
}

@Service
class PatientSearchServiceImpl(
    private val patientService: PatientService,
) : PatientSearchService {

    @Transactional
    override fun getPatients(patientSearchCriteria: PatientSearchCriteria): PageResult<PatientResult> {
        patientService.findAll(patientSearchCriteria).map { }
        TODO()
    }
}