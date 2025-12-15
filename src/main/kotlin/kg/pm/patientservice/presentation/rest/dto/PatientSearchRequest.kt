package kg.pm.patientservice.presentation.rest.dto

import kg.pm.patientservice.application.dto.patient.PatientSortField
import kg.pm.patientservice.presentation.rest.common.PageRequestDto
import kg.pm.patientservice.presentation.rest.common.SortOrderDto
import kg.pm.patientservice.shared.SortDirection
import java.time.LocalDate

data class PatientSearchRequest(
    val paging: PageRequestDto = PageRequestDto(),
    val filter: PatientSearchFilterDto = PatientSearchFilterDto(),
    val sortBy: SortOrderDto<PatientSortField> = SortOrderDto(PatientSortField.ID, SortDirection.ASC)
)

data class PatientSearchFilterDto(
    val name: String? = null,
    val email: String? = null,
    val registeredFrom: LocalDate? = null,
    val registeredTo: LocalDate? = null
)