package kg.pm.patientservice.domain.core.service

import kg.pm.patientservice.domain.core.model.Patient
import kg.pm.patientservice.domain.core.repository.PatientRepository
import kg.pm.patientservice.domain.core.service.dto.PatientSearch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PatientService {
    fun findAll(patientSearch: PatientSearch): List<Patient>
}

@Service
class PatientServiceImpl(
    private val patientRepository: PatientRepository
) : PatientService {

    @Transactional(readOnly = true)
    override fun findAll(patientSearch: PatientSearch): List<Patient> {
        TODO()
    }
}