package kg.pm.patientservice.infrastructure.exposed.repository

import kg.pm.patientservice.application.dto.patient.PatientSearchCriteria
import kg.pm.patientservice.application.dto.patient.PatientSortField
import kg.pm.patientservice.domain.core.model.Patient
import kg.pm.patientservice.infrastructure.exposed.PatientsTable
import kg.pm.patientservice.shared.PageResult
import kg.pm.patientservice.shared.SortDirection
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.jdbc.Query
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import kotlin.math.ceil
import kg.pm.patientservice.domain.core.repository.PatientRetrieverRepository as PatientRepositoryInterface

@Repository
class PatientRepositoryImpl : PatientRepositoryInterface {

    @Transactional(readOnly = true)
    override fun search(searchCriteria: PatientSearchCriteria): PageResult<Patient> {
        val filter = searchCriteria.filter

        var baseQuery: Query = PatientsTable.selectAll()

        filter.name?.isNotBlank().let {
            baseQuery.andWhere { PatientsTable.name like "%${filter.name}%" }
        }
        filter.email?.isNotBlank().let {
            baseQuery = baseQuery.andWhere { PatientsTable.email like "%${filter.email}%" }
        }
        
//        if (filter.registeredFrom != null && filter.registeredTo != null) {
//            val fromDateTime = filter.registeredFrom.atStartOfDay()
//            val toDateTime = filter.registeredTo.atTime(23, 59, 59)
//            baseQuery = baseQuery.andWhere { PatientsTable.registeredDate.between(fromDateTime, toDateTime) }
//        } else if (filter.registeredFrom != null) {
//            val fromDateTime: LocalDateTime = filter.registeredFrom.atStartOfDay()!!
//            baseQuery = baseQuery.andWhere { PatientsTable.registeredDate.greaterEq(fromDateTime) }
//        } else if (filter.registeredTo != null) {
//            val toDateTime = filter.registeredTo.atTime(23, 59, 59)
//            baseQuery = baseQuery.andWhere { PatientsTable.registeredDate lessEq toDateTime }
//        }

        val sortOrder: SortOrder = when (searchCriteria.sortBy.direction) {
            SortDirection.ASC -> SortOrder.ASC
            SortDirection.DESC -> SortOrder.DESC
        }
        val sortedQuery = when (searchCriteria.sortBy.property) {
            PatientSortField.ID -> baseQuery.orderBy(PatientsTable.id to sortOrder)
            PatientSortField.NAME -> baseQuery.orderBy(PatientsTable.name to sortOrder)
            PatientSortField.EMAIL -> baseQuery.orderBy(PatientsTable.email to sortOrder)
            PatientSortField.REGISTERED_DATE -> baseQuery.orderBy(PatientsTable.registeredDate to sortOrder)
        }

        // Apply pagination
        val paging = searchCriteria.paging
        val offset: Int = paging.page * paging.size

        val results = sortedQuery
            .limit(paging.size)
            .offset(offset.toLong())
            .map { it.toPatient() }

        val total: Long = baseQuery.count()
        val totalPages = if (paging.size == 0) 1 else ceil(total.toDouble() / paging.size).toInt()
        
        return PageResult(
            content = results,
            page = paging.page,
            size = paging.size,
            totalElements = total,
            totalPages = totalPages
        )
    }
}

/**
 * Extension function to map Exposed ResultRow to Domain Patient entity.
 * Converts TIMESTAMP to LocalDate for registeredDate.
 */
private fun ResultRow.toPatient(): Patient {
    return Patient(
        id = this[PatientsTable.id].value,
        name = this[PatientsTable.name],
        email = this[PatientsTable.email],
        address = this[PatientsTable.address],
        dateOfBirth = this[PatientsTable.dateOfBirth],
        registeredDate = this[PatientsTable.registeredDate]
    )
}