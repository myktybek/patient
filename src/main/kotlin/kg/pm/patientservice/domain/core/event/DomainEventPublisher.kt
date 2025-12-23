package kg.pm.patientservice.domain.core.event

import kg.pm.patientservice.domain.core.model.AggregateRoot

interface DomainEventPublisher {
    fun publish(event: DomainEvent)
    fun publish(events: List<DomainEvent>)
    fun publishAndClear(aggregate: AggregateRoot) {
        publish(aggregate.domainEvents)
        aggregate.clearDomainEvents()
    }
}