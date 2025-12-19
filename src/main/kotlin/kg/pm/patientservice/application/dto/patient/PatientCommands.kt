package kg.pm.patientservice.application.dto.patient

import java.time.LocalDate

/**
 * Command DTOs for Patient write operations.
 * These are application-layer DTOs that will be mapped to domain value objects.
 */

/**
 * Command to register a new patient.
 */
data class RegisterPatientCommand(
    val email: String,
    val name: String?,
    val address: AddressCommand?,
    val dateOfBirth: LocalDate?
)

/**
 * Command to update a patient's profile information.
 */
data class UpdatePatientProfileCommand(
    val patientId: Long,
    val name: String?,
    val address: AddressCommand?,
    val dateOfBirth: LocalDate?
)

/**
 * Command to change a patient's email address.
 */
data class ChangePatientEmailCommand(
    val patientId: Long,
    val newEmail: String
)

/**
 * Structured address data for commands.
 */
data class AddressCommand(
    val street: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
)
