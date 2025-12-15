package kg.pm.patientservice.presentation.rest

import kg.pm.patientservice.application.PatientSearchService
import kg.pm.patientservice.application.dto.patient.PatientResult
import kg.pm.patientservice.application.dto.patient.PatientSearchCriteria
import kg.pm.patientservice.presentation.rest.common.PageResponseDto
import kg.pm.patientservice.presentation.rest.common.toPageResponse
import kg.pm.patientservice.presentation.rest.dto.PatientResponse
import kg.pm.patientservice.presentation.rest.dto.PatientSearchRequest
import kg.pm.patientservice.presentation.rest.mapper.PatientSearchMapper
import kg.pm.patientservice.shared.PageResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/patients")
class PatientController(
    val patientSearchService: PatientSearchService,
    val patientSearchMapper: PatientSearchMapper
) {

    @GetMapping
    fun getPatients(patientSearchRequest: PatientSearchRequest): PageResponseDto<PatientResponse> {
        val searchCriteria: PatientSearchCriteria = patientSearchMapper.toPatientSearchCriteria(patientSearchRequest)
        val patients: PageResult<PatientResult> = patientSearchService.getPatients(searchCriteria)
        return patients.toPageResponse { patientSearchMapper.toPatientResponse(it) }
    }
}