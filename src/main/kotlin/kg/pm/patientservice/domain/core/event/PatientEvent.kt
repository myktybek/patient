package kg.pm.patientservice.domain.core.event

import java.time.LocalDate

data class PatientRegisteredEvent(
    val email: String,
    val registeredDate: LocalDate
) : BaseDomainEvent() {
    override val eventType: String = "PatientRegistered"
}

data class PatientUpdatedEvent(
    val patientId: Long
) : BaseDomainEvent() {
    override val eventType: String = "PatientUpdated"
}

data class PatientDeletedEvent(
    val patientId: Long,
    val email: String
) : BaseDomainEvent() {
    override val eventType: String = "PatientDeleted"
}

// Business-specific events
data class PatientEmailChangedEvent(
    val patientId: Long,
    val oldEmail: String,
    val newEmail: String
) : BaseDomainEvent() {
    override val eventType: String = "PatientEmailChanged"
}

data class PatientAddressChangedEvent(
    val patientId: Long,
    val newAddress: String
) : BaseDomainEvent() {
    override val eventType: String = "PatientAddressChanged"
}
