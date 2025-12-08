package kg.pm.patientservice.presentation.rest.pagination

data class PageRequestDto(
    val page: Int = 0,
    val size: Int = 100,
)

data class SortOrder(
    val property: String,
    val direction: SortDirection = SortDirection.ASC
)

enum class SortDirection { ASC, DESC }

data class PageResponseDto<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
)