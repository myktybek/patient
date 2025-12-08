package kg.pm.patientservice.presentation.rest.dto

import kg.pm.patientservice.application.pagination.PageRequestDto
import kg.pm.patientservice.application.pagination.SortOrder
import java.util.*

data class PatientSearchRequest(
    val paging: PageRequestDto = PageRequestDto(),
    val filter: PatientSearchFilter = PatientSearchFilter(),
    val sort: SortedSet<SortOrder> = sortedSetOf()
)

data class PatientSearchFilter(
    val name: String? = null,
    val email: String? = null,
)