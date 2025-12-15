package kg.pm.patientservice.application.dto.patient

import kg.pm.patientservice.shared.PageCriteria
import kg.pm.patientservice.shared.SortDirection
import java.time.LocalDate

data class PatientSearchCriteria(
    val paging: PageCriteria,
    val filter: PatientSearchFilter,
    val sortBy: PatientSearchSortOrder
)

data class PatientSearchFilter(
    val name: String? = null,
    val email: String? = null,
    val registeredFrom: LocalDate? = null,
    val registeredTo: LocalDate? = null
)

data class PatientSearchSortOrder(
    val property: PatientSortField,
    val direction: SortDirection
)

enum class PatientSortField {
    ID,
    NAME,
    EMAIL,
    REGISTERED_DATE
}