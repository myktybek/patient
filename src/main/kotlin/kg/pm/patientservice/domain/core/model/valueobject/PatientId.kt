package kg.pm.patientservice.domain.core.model.valueobject

/**
 * Value object representing a unique identifier for a Patient.
 * Wraps a Long to avoid primitive obsession and provide type safety.
 */
data class PatientId(val value: Long) {
    init {
        require(value > 0) { "Patient ID must be positive" }
    }
}
