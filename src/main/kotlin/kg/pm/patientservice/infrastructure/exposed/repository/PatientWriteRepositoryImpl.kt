package kg.pm.patientservice.infrastructure.exposed.repository

import kg.pm.patientservice.domain.core.model.Patient
import kg.pm.patientservice.domain.core.model.valueobject.Email
import kg.pm.patientservice.domain.core.model.valueobject.PatientId
import kg.pm.patientservice.domain.core.repository.PatientRepository
import kg.pm.patientservice.infrastructure.exposed.PatientsTable
import kg.pm.patientservice.infrastructure.exposed.mapper.AddressJsonMapper
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.*
import org.jetbrains.exposed.v1.jdbc.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * Exposed-based implementation of PatientRepository for write operations.
 * Handles mapping between domain value objects and database columns.
 */
@Repository
class PatientWriteRepositoryImpl : PatientRepository {

    @Transactional
    override fun save(patient: Patient): Patient {
        return if (patient.id == null) {
            insert(patient)
        } else {
            update(patient)
            patient
        }
    }

    @Transactional(readOnly = true)
    override fun findById(id: PatientId): Patient? {
        return PatientsTable
            .selectAll()
            .where { PatientsTable.id eq id.value }
            .singleOrNull()
            ?.toPatient()
    }

    @Transactional(readOnly = true)
    override fun findByEmail(email: Email): Patient? {
        return PatientsTable
            .selectAll()
            .where { PatientsTable.email eq email.value }
            .singleOrNull()
            ?.toPatient()
    }

    @Transactional(readOnly = true)
    override fun existsByEmail(email: Email, excludePatientId: PatientId?): Boolean {
        var query = PatientsTable
            .selectAll()
            .where { PatientsTable.email eq email.value }

        if (excludePatientId != null) {
            query = query.andWhere { PatientsTable.id neq excludePatientId.value }
        }

        return query.count() > 0
    }

    @Transactional
    override fun deleteById(id: PatientId) {
        PatientsTable.deleteWhere { PatientsTable.id eq id.value }
    }

    private fun insert(patient: Patient): Patient {
        val insertedId = PatientsTable.insert {
            it[name] = patient.getName()
            it[email] = patient.getEmail().value
            it[address] = patient.getAddress()?.let { addr -> AddressJsonMapper.toJson(addr) }
            it[dateOfBirth] = patient.dateOfBirth
            it[registeredDate] = patient.registeredDate
        } get PatientsTable.id

        // Reconstitute patient with assigned ID
        return Patient.reconstitute(
            id = PatientId(insertedId.value),
            email = patient.getEmail(),
            name = patient.getName(),
            address = patient.getAddress(),
            dateOfBirth = patient.dateOfBirth,
            registeredDate = patient.registeredDate
        )
    }

    private fun update(patient: Patient) {
        requireNotNull(patient.id) { "Patient ID cannot be null for update" }

        PatientsTable.update({ PatientsTable.id eq patient.id.value }) {
            it[name] = patient.getName()
            it[email] = patient.getEmail().value
            it[address] = patient.getAddress()?.let { addr -> AddressJsonMapper.toJson(addr) }
            it[dateOfBirth] = patient.dateOfBirth
            it[registeredDate] = patient.registeredDate
        }
    }
}

/**
 * Extension function to map Exposed ResultRow to Domain Patient entity.
 * Handles value object reconstitution and JSON deserialization for Address.
 */
private fun ResultRow.toPatient(): Patient {
    return Patient.reconstitute(
        id = PatientId(this[PatientsTable.id].value),
        email = Email(this[PatientsTable.email]),
        name = this[PatientsTable.name]?.let { Name(it) },
        address = AddressJsonMapper.fromJson(this[PatientsTable.address]),
        dateOfBirth = this[PatientsTable.dateOfBirth],
        registeredDate = this[PatientsTable.registeredDate]
    )
}
