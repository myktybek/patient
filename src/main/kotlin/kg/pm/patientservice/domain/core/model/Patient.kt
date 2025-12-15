package kg.pm.patientservice.domain.core.model

import java.time.LocalDate

data class Patient(
    val id: Long? = null,

    val name: String?,

    val email: String,

    val address: String?,

    val dateOfBirth: LocalDate?,

    val registeredDate: LocalDate
)