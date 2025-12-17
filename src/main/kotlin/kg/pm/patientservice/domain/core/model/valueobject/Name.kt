package kg.pm.patientservice.domain.core.model.valueobject

/**
 * Value object representing a person's name.
 * Enforces basic validation rules and normalizes whitespace.
 */
data class Name(val value: String) {
    init {
        val trimmed = value.trim()
        require(trimmed.isNotBlank()) { "Name cannot be blank" }
        require(trimmed.length in 1..255) { "Name must be between 1 and 255 characters" }
    }

    /**
     * Returns the normalized name with trimmed whitespace.
     */
    fun normalized(): String = value.trim()
}
