package kg.pm.patientservice.presentation.rest.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Request DTO for structured address information.
 */
data class AddressRequest(
    @field:NotBlank(message = "Street is required")
    @field:Size(max = 255, message = "Street cannot exceed 255 characters")
    val street: String,

    @field:NotBlank(message = "City is required")
    @field:Size(max = 100, message = "City cannot exceed 100 characters")
    val city: String,

    @field:NotBlank(message = "State is required")
    @field:Size(max = 100, message = "State cannot exceed 100 characters")
    val state: String,

    @field:NotBlank(message = "Postal code is required")
    @field:Size(max = 20, message = "Postal code cannot exceed 20 characters")
    val postalCode: String,

    @field:NotBlank(message = "Country is required")
    @field:Size(max = 100, message = "Country cannot exceed 100 characters")
    val country: String
)
