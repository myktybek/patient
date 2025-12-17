package kg.pm.patientservice.presentation.rest.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate

/**
 * Request DTO for registering a new patient.
 */
data class RegisterPatientRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email must be valid")
    @field:Size(max = 255, message = "Email cannot exceed 255 characters")
    val email: String,

    @field:Size(max = 255, message = "Name cannot exceed 255 characters")
    val name: String?,

    @field:Valid
    val address: AddressRequest?,

    val dateOfBirth: LocalDate?
)

/**
 * Request DTO for updating patient profile.
 */
data class UpdatePatientProfileRequest(
    @field:Size(max = 255, message = "Name cannot exceed 255 characters")
    val name: String?,

    @field:Valid
    val address: AddressRequest?,

    val dateOfBirth: LocalDate?
)

/**
 * Request DTO for changing patient email.
 */
data class ChangePatientEmailRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email must be valid")
    @field:Size(max = 255, message = "Email cannot exceed 255 characters")
    val newEmail: String
)
