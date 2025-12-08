package kg.pm.patientservice.domain.core.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import kg.pm.patientservice.domain.core.model.base.AuditEntity
import java.time.LocalDate

@Entity
@Table(name = "patient")
data class Patient(

    var name: String?,

    @Column(unique = true, nullable = false)
    @get:Email
    val email: String,

    @Column(name = "address")
    var address: String?,

    var dateOfBirth: LocalDate?,

    val registeredDate: LocalDate
) : AuditEntity()