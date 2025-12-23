package kg.pm.patientservice.domain.core.model

import kg.pm.patientservice.domain.core.event.DomainEvent

abstract class AggregateRoot {
    abstract val domainEvents: List<DomainEvent>
    abstract fun clearDomainEvents()
}