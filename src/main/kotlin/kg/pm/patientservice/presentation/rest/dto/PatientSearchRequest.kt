package kg.pm.patientservice.presentation.rest.dto

import jakarta.validation.constraints.Size
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

    @field:Size(max = 255, message = "Name cannot exceed 255 characters")
    val name: String? = null,

    @field:Size(max = 255, message = "Email cannot exceed 255 characters")
    val email: String? = null,

    val registeredFrom: LocalDate? = null,
    val registeredTo: LocalDate? = null
)