package kg.pm.patientservice.application.dto.patient

import java.time.LocalDate

data class PatientResult(
    val id: Long,
    val name: String?,
    val email: String,
    val address: String?,
    val dateOfBirth: LocalDate?,
    val registeredDate: LocalDate
)