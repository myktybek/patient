package kg.pm.patientservice.domain.core.model

import kg.pm.patientservice.domain.core.event.*
import kg.pm.patientservice.domain.core.model.valueobject.Address
import kg.pm.patientservice.domain.core.model.valueobject.Email
import kg.pm.patientservice.domain.core.model.valueobject.PatientId
import java.time.LocalDate

/**
 * Patient aggregate root.
 * Encapsulates patient business logic and invariants.
 * Publishes domain events for state changes.
 */
class Patient private constructor(
    val id: PatientId?,
    private var name: String?,
    private var email: Email,
    private var address: Address?,
    private var dateOfBirth: LocalDate?,
    val registeredDate: LocalDate
) : AggregateRoot() {
    fun getName(): String? = name
    fun getEmail(): Email = email
    fun getAddress(): Address? = address
    fun getDateOfBirth(): LocalDate? = dateOfBirth

    // Domain events collection
    private val _domainEvents = mutableListOf<DomainEvent>()
    override val domainEvents: List<DomainEvent> get() = _domainEvents.toList()

    override fun clearDomainEvents() {
        _domainEvents.clear()
    }

    companion object {
        fun register(
            email: Email,
            name: String?,
            address: Address?,
            dateOfBirth: LocalDate?,
            registeredDate: LocalDate = LocalDate.now()
        ): Patient {
            // Validate name if provided
            val validatedName = name?.let {
                val trimmed = it.trim()
                require(trimmed.isNotBlank()) { "Name cannot be blank" }
                require(trimmed.length in 1..255) { "Name must be between 1 and 255 characters" }
                trimmed
            }

            val patient = Patient(
                id = null,
                name = validatedName,
                email = email,
                address = address,
                dateOfBirth = dateOfBirth,
                registeredDate = registeredDate
            )

            patient._domainEvents.add(
                PatientRegisteredEvent(
                    email = email.value,
                    registeredDate = registeredDate
                )
            )

            return patient
        }

        /**
         * Reconstitute a patient from the database.
         * Used by the repository layer.
         * Does not publish events.
         */
        fun reconstitute(
            id: PatientId,
            email: Email,
            name: String?,
            address: Address?,
            dateOfBirth: LocalDate?,
            registeredDate: LocalDate
        ): Patient {
            return Patient(
                id = id,
                name = name,
                email = email,
                address = address,
                dateOfBirth = dateOfBirth,
                registeredDate = registeredDate
            )
        }
    }

    /**
     * Changes the patient's email address.
     * Publishes PatientEmailChangedEvent if the email actually changed.
     *
     * Note: Email uniqueness should be validated by the domain service before calling this method.
     */
    fun changeEmail(newEmail: Email) {
        if (email == newEmail) return // No change

        val oldEmail = email
        email = newEmail

        requireNotNull(id) { "Cannot change email for a patient that hasn't been persisted" }

        _domainEvents.add(
            PatientEmailChangedEvent(
                patientId = id.value, oldEmail = oldEmail.value, newEmail = newEmail.value
            )
        )
    }

    fun updateProfile(
        name: String?, address: Address?, dateOfBirth: LocalDate?
    ) {
        requireNotNull(id) { "Cannot update profile for a patient that hasn't been persisted" }

        // Validate name if provided
        name?.let {
            val trimmed = it.trim()
            require(trimmed.isNotBlank()) { "Name cannot be blank" }
            require(trimmed.length in 1..255) { "Name must be between 1 and 255 characters" }
        }

        this.name = name?.trim()
        this.address = address
        this.dateOfBirth = dateOfBirth

        _domainEvents.add(
            PatientUpdatedEvent(patientId = id.value)
        )
    }
//
//    fun updateAddress(newAddress: Address?) {
//        requireNotNull(id) { "Cannot update address for a patient that hasn't been persisted" }
//
//        if (address == newAddress) return // No change
//
//        this.address = newAddress
//
//        // Only publish event if address is not null (meaningful change)
//        if (newAddress != null) {
//            _domainEvents.add(
//                PatientAddressChangedEvent(
//                    patientId = id.value,
//                    newAddress = newAddress.toFormattedString()
//                )
//            )
//        }
//    }

    fun markForDeletion() {
        requireNotNull(id) { "Cannot delete a patient that hasn't been persisted" }

        _domainEvents.add(
            PatientDeletedEvent(
                patientId = id.value,
                email = email.value
            )
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Patient) return false
        if (id == null || other.id == null) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Patient(id=$id, email=${email.value}, name=$name, registeredDate=$registeredDate)"
    }
}
