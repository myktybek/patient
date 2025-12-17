package kg.pm.patientservice.domain.core.event

interface DomainEventPublisher {
    fun publish(event: DomainEvent)
    fun publish(events: List<DomainEvent>)
}