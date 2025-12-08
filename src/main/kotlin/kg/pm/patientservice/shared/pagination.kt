package kg.pm.patientservice.shared

data class PageCriteria(
    val page: Int,
    val size: Int,
    val sort: List<SortOrder>
)

data class SortOrder(
    val property: String,
    val direction: SortDirection
)

enum class SortDirection { ASC, DESC }

data class PageResult<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
)