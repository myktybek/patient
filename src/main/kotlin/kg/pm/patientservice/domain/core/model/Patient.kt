package kg.pm.patientservice.domain.core.model

import kg.pm.patientservice.domain.core.event.*
import kg.pm.patientservice.domain.core.model.valueobject.*
import java.time.LocalDate

/**
 * Patient aggregate root.
 * Encapsulates patient business logic and invariants.
 * Publishes domain events for state changes.
 */
class Patient private constructor(
    val id: PatientId?,
    private var name: Name?,
    private var email: Email,
    private var address: Address?,
    val dateOfBirth: LocalDate?,
    val registeredDate: LocalDate
) {
    // Public getters for encapsulated fields
    fun getName(): Name? = name
    fun getEmail(): Email = email
    fun getAddress(): Address? = address

    // Domain events collection
    private val _domainEvents = mutableListOf<DomainEvent>()
    val domainEvents: List<DomainEvent> get() = _domainEvents.toList()

    fun clearDomainEvents() {
        _domainEvents.clear()
    }

    companion object {
        /**
         * Factory method to register a new patient.
         * Publishes PatientRegisteredEvent.
         */
        fun register(
            email: Email,
            name: Name?,
            address: Address?,
            dateOfBirth: LocalDate?,
            registeredDate: LocalDate = LocalDate.now()
        ): Patient {
            val patient = Patient(
                id = null, // Will be assigned after persistence
                name = name,
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
            name: Name?,
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
                patientId = id.value,
                oldEmail = oldEmail.value,
                newEmail = newEmail.value
            )
        )
    }

    /**
     * Updates the patient's profile information.
     * Publishes PatientUpdatedEvent.
     */
    fun updateProfile(
        name: Name?,
        address: Address?,
        dateOfBirth: LocalDate?
    ) {
        requireNotNull(id) { "Cannot update profile for a patient that hasn't been persisted" }

        this.name = name
        this.address = address
        // dateOfBirth is immutable after initial registration (final val in constructor)

        _domainEvents.add(
            PatientUpdatedEvent(patientId = id.value)
        )
    }

    /**
     * Updates just the patient's address.
     * Publishes PatientAddressChangedEvent.
     */
    fun updateAddress(newAddress: Address?) {
        requireNotNull(id) { "Cannot update address for a patient that hasn't been persisted" }

        if (address == newAddress) return // No change

        this.address = newAddress

        // Only publish event if address is not null (meaningful change)
        if (newAddress != null) {
            _domainEvents.add(
                PatientAddressChangedEvent(
                    patientId = id.value,
                    newAddress = newAddress.toFormattedString()
                )
            )
        }
    }

    /**
     * Marks the patient for deletion.
     * Publishes PatientDeletedEvent.
     */
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
        return "Patient(id=$id, email=${email.value}, name=${name?.value}, registeredDate=$registeredDate)"
    }
}
