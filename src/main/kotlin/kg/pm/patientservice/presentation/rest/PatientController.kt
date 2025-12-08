package kg.pm.patientservice.infrastructure.presentation.rest

import kg.pm.patientservice.application.PatientSearchService
import kg.pm.patientservice.application.dto.PatientResponseDto
import kg.pm.patientservice.application.dto.PatientSearchRequest
import kg.pm.patientservice.application.pagination.PageResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/patients")
class PatientController(
    val patientSearchService: PatientSearchService
) {

    @GetMapping
    fun getPatients(patientSearchRequest: PatientSearchRequest): PageResponse<PatientResponseDto> {
        return patientSearchService.getPatients(patientSearchRequest)
    }
}