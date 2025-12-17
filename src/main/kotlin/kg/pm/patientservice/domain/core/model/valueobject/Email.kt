package kg.pm.patientservice.domain.core.model.valueobject

/**
 * Value object representing an email address.
 * Enforces format validation and immutability.
 */
data class Email(val value: String) {
    init {
        require(value.isNotBlank()) { "Email cannot be blank" }
        require(value.length <= 255) { "Email cannot exceed 255 characters" }
        require(EMAIL_REGEX.matches(value)) { "Invalid email format: $value" }
    }

    companion object {
        private val EMAIL_REGEX = Regex(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        )
    }
}
