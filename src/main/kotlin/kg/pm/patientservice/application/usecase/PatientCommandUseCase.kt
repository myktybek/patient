package kg.pm.patientservice.application.usecase

import kg.pm.patientservice.application.dto.patient.ChangePatientEmailCommand
import kg.pm.patientservice.application.dto.patient.RegisterPatientCommand
import kg.pm.patientservice.application.dto.patient.UpdatePatientProfileCommand
import kg.pm.patientservice.application.exception.PatientNotFoundException
import kg.pm.patientservice.application.mapper.AddressCommandMapper
import kg.pm.patientservice.domain.core.event.DomainEventPublisher
import kg.pm.patientservice.domain.core.model.Patient
import kg.pm.patientservice.domain.core.model.valueobject.Email
import kg.pm.patientservice.domain.core.model.valueobject.PatientId
import kg.pm.patientservice.domain.core.repository.PatientRepository
import kg.pm.patientservice.domain.core.service.PatientService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PatientCommandUseCase(
    private val patientRepository: PatientRepository,
    private val addressCommandMapper: AddressCommandMapper,
    private val eventPublisher: DomainEventPublisher,
    private val patientService: PatientService
) {

    @Transactional
    fun execute(command: UpdatePatientProfileCommand) {
        val patient = patientRepository.findById(PatientId(command.patientId))
            ?: throw PatientNotFoundException("Patient with ID ${command.patientId} not found")

        val address = command.address?.let { addressCommandMapper.toDomain(command.address) }

        patient.updateProfile(
            name = command.name,
            address = address,
            dateOfBirth = command.dateOfBirth
        )

        patientRepository.save(patient)

        eventPublisher.publishAndClear(patient)
    }

    @Transactional
    fun execute(command: RegisterPatientCommand): Long {
        val email = Email(command.email)
        val address = command.address?.let { addressCommandMapper.toDomain(it) }

        patientService.ensureEmailIsUnique(email)

        val patient = Patient.register(
            email = email,
            name = command.name,
            address = address,
            dateOfBirth = command.dateOfBirth
        )
        val savedPatient: Patient = patientRepository.save(patient)

        eventPublisher.publishAndClear(patient)

        return savedPatient.id!!.value
    }


    @Transactional
    fun execute(command: ChangePatientEmailCommand) {
        val patientId = PatientId(command.patientId)
        val newEmail = Email(command.newEmail)

        val patient = patientRepository.findById(patientId)
            ?: throw PatientNotFoundException("Patient with ID ${command.patientId} not found")

        patientService.ensureEmailIsUnique(newEmail, excludePatientId = patientId)

        patient.changeEmail(newEmail)

        patientRepository.save(patient)

        eventPublisher.publishAndClear(patient)
    }

    @Transactional
    fun execute(patientId: Long) {
        val id = PatientId(patientId)
        val patient = patientRepository.findById(id)
            ?: throw PatientNotFoundException("Patient with ID $patientId not found")

        patient.markForDeletion()

        eventPublisher.publishAndClear(patient)

        patientRepository.deleteById(id)
    }
}
