package kg.pm.patientservice.shared

data class PageCriteria(
    val page: Int,
    val size: Int,
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

fun <D, R> PageResult<D>.transform(mapper: (D) -> R): PageResult<R> =
    PageResult(
        content = this.content.map(mapper),
        page = this.page,
        size = this.size,
        totalElements = this.totalElements,
        totalPages = this.totalPages
    )