package kg.pm.patientservice.presentation.rest

import jakarta.validation.Valid
import kg.pm.patientservice.application.usecase.PatientCommandUseCase
import kg.pm.patientservice.presentation.rest.dto.ChangePatientEmailRequest
import kg.pm.patientservice.presentation.rest.dto.PatientIdResponse
import kg.pm.patientservice.presentation.rest.dto.RegisterPatientRequest
import kg.pm.patientservice.presentation.rest.dto.UpdatePatientProfileRequest
import kg.pm.patientservice.presentation.rest.mapper.PatientCommandMapper
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/patients")
class PatientController(
    private val patientCommandMapper: PatientCommandMapper,
    private val patientCommandUseCase: PatientCommandUseCase
) {
    @PostMapping
    fun registerPatient(@Valid @RequestBody request: RegisterPatientRequest): PatientIdResponse {
        val command = patientCommandMapper.toRegisterPatientCommand(request)
        val patientId: Long = patientCommandUseCase.execute(command)
        return PatientIdResponse(patientId)
    }

    @PutMapping("/{id}")
    fun updatePatientProfile(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdatePatientProfileRequest
    ) {
        val command = patientCommandMapper.toUpdatePatientProfileCommand(id, request)
        patientCommandUseCase.execute(command)
    }

    @PutMapping("/{id}/email")
    fun changePatientEmail(
        @PathVariable id: Long,
        @Valid @RequestBody request: ChangePatientEmailRequest
    ) {
        val command = patientCommandMapper.toChangePatientEmailCommand(id, request)
        patientCommandUseCase.execute(command)
    }

    @DeleteMapping("/{id}")
    fun deletePatient(@PathVariable id: Long) {
        patientCommandUseCase.execute(id)
    }
}