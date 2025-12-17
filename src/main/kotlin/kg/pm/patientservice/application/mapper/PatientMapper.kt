package kg.pm.patientservice.application.mapper

import kg.pm.patientservice.application.dto.patient.PatientResult
import kg.pm.patientservice.domain.core.model.Patient
import org.springframework.stereotype.Component

@Component
class PatientMapper {

    fun toPatientResult(patient: Patient): PatientResult {
        return PatientResult(
            id = patient.id?.value ?: throw IllegalStateException("Patient ID cannot be null"),
            name = patient.getName()?.value,
            email = patient.getEmail().value,
            address = patient.getAddress()?.toFormattedString(),
            dateOfBirth = patient.dateOfBirth,
            registeredDate = patient.registeredDate
        )
    }
}
