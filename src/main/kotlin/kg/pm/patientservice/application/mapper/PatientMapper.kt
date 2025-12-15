package kg.pm.patientservice.application.mapper

import kg.pm.patientservice.application.dto.patient.PatientResult
import kg.pm.patientservice.domain.core.model.Patient
import org.springframework.stereotype.Component

@Component
class PatientMapper {

    fun toPatientResult(patient: Patient): PatientResult {
        return PatientResult(
            id = patient.id ?: throw IllegalStateException("Patient ID cannot be null"),
            name = patient.name,
            email = patient.email,
            address = patient.address,
            dateOfBirth = patient.dateOfBirth,
            registeredDate = patient.registeredDate
        )
    }
}