package kg.pm.patientservice.presentation.rest.mapper

import kg.pm.patientservice.domain.dto.PatientSearchCriteria
import kg.pm.patientservice.presentation.rest.dto.PatientSearchRequest
import org.springframework.stereotype.Component

@Component
class PatientSearchRequestMapper {

    fun toPatientSearchCriteria(patientSearchRequest: PatientSearchRequest): PatientSearchCriteria {
        TODO()
    }
}