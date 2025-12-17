package kg.pm.patientservice.domain.core.model.valueobject

/**
 * Value object representing a structured physical address.
 * All fields are validated for proper format and reasonable length.
 */
data class Address(
    val street: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
) {
    init {
        require(street.isNotBlank()) { "Street cannot be blank" }
        require(street.length <= 255) { "Street cannot exceed 255 characters" }

        require(city.isNotBlank()) { "City cannot be blank" }
        require(city.length <= 100) { "City cannot exceed 100 characters" }

        require(state.isNotBlank()) { "State cannot be blank" }
        require(state.length <= 100) { "State cannot exceed 100 characters" }

        require(postalCode.isNotBlank()) { "Postal code cannot be blank" }
        require(postalCode.length <= 20) { "Postal code cannot exceed 20 characters" }

        require(country.isNotBlank()) { "Country cannot be blank" }
        require(country.length <= 100) { "Country cannot exceed 100 characters" }
    }

    /**
     * Returns a human-readable formatted address string.
     */
    fun toFormattedString(): String {
        return "$street, $city, $state $postalCode, $country"
    }
}
