package kg.pm.patientservice.infrastructure.event

import kg.pm.patientservice.domain.core.event.DomainEvent
import kg.pm.patientservice.domain.core.event.DomainEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component


@Component
class SpringDomainEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) : DomainEventPublisher {

    override fun publish(event: DomainEvent) {
        applicationEventPublisher.publishEvent(event)
    }

    override fun publish(events: List<DomainEvent>) {
        events.forEach { event ->
            applicationEventPublisher.publishEvent(event)
        }
    }
}
