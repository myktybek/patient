package kg.pm.patientservice.domain.core.repository

import kg.pm.patientservice.domain.core.model.Patient
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository : JpaRepository<Patient, Long> {
}