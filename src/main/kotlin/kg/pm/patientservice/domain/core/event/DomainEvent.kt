package kg.pm.patientservice.domain.core.event

import java.time.LocalDateTime
import java.util.UUID

interface DomainEvent {
    val eventId: String
    val occurredOn: LocalDateTime
    val eventType: String
}

abstract class BaseDomainEvent : DomainEvent {
    override val eventId: String = UUID.randomUUID().toString()
    override val occurredOn: LocalDateTime = LocalDateTime.now()
}
