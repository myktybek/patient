package kg.pm.patientservice.presentation.rest.dto

import java.time.LocalDate

data class PatientResponse(
    val id: Long,
    val name: String?,
    val email: String,
    val address: String?,
    val dateOfBirth: LocalDate?,
    val registeredDate: LocalDate
)
