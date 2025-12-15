package kg.pm.patientservice.presentation.rest.mapper

import kg.pm.patientservice.application.dto.patient.*
import kg.pm.patientservice.presentation.rest.dto.PatientResponse
import kg.pm.patientservice.presentation.rest.dto.PatientSearchRequest
import kg.pm.patientservice.shared.PageCriteria
import org.springframework.stereotype.Component

@Component
class PatientSearchMapper {

    fun toPatientSearchCriteria(searchRequest: PatientSearchRequest): PatientSearchCriteria {
        return PatientSearchCriteria(
            paging = PageCriteria(
                page = searchRequest.paging.page,
                size = searchRequest.paging.size,
            ),
            filter = PatientSearchFilter(
                name = searchRequest.filter.name,
                email = searchRequest.filter.email,
                registeredFrom = searchRequest.filter.registeredFrom,
                registeredTo = searchRequest.filter.registeredTo
            ),
            sortBy = PatientSearchSortOrder(
                property = searchRequest.sortBy.property,
                direction = searchRequest.sortBy.direction
            )
        )
    }

    fun toPatientResponse(result: PatientResult): PatientResponse {
        return PatientResponse(
            id = result.id,
            name = result.name,
            email = result.email,
            address = result.address,
            dateOfBirth = result.dateOfBirth,
            registeredDate = result.registeredDate
        )
    }
}