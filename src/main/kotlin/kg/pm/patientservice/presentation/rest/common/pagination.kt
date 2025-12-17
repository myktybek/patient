package kg.pm.patientservice.presentation.rest.common

import jakarta.validation.constraints.Size
import kg.pm.patientservice.shared.PageResult
import kg.pm.patientservice.shared.SortDirection

data class PageRequestDto(
    val page: Int = 0,

    @field:Size(max = 100)
    val size: Int = 100,
)

data class SortOrderDto<T>(
    val property: T,
    val direction: SortDirection = SortDirection.ASC
)

data class PageResponseDto<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
)

fun <D, R> PageResult<D>.toPageResponse(mapper: (D) -> R): PageResponseDto<R> =
    PageResponseDto(
        content = this.content.map(mapper),
        page = this.page,
        size = this.size,
        totalElements = this.totalElements,
        totalPages = this.totalPages
    )