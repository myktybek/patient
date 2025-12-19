package kg.pm.patientservice.presentation.rest

import jakarta.validation.Valid
import kg.pm.patientservice.application.usecase.PatientSearchUseCase
import kg.pm.patientservice.application.dto.patient.PatientResult
import kg.pm.patientservice.application.dto.patient.PatientSearchCriteria
import kg.pm.patientservice.application.usecase.ChangePatientEmailUseCase
import kg.pm.patientservice.application.usecase.DeletePatientUseCase
import kg.pm.patientservice.application.usecase.RegisterPatientUseCase
import kg.pm.patientservice.application.usecase.UpdatePatientProfileUseCase
import kg.pm.patientservice.presentation.rest.common.PageResponseDto
import kg.pm.patientservice.presentation.rest.common.toPageResponse
import kg.pm.patientservice.presentation.rest.dto.*
import kg.pm.patientservice.presentation.rest.mapper.PatientCommandMapper
import kg.pm.patientservice.presentation.rest.mapper.PatientSearchMapper
import kg.pm.patientservice.shared.PageResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/patients")
class PatientController(
    private val patientSearchUseCase: PatientSearchUseCase,
    private val patientSearchMapper: PatientSearchMapper,
    private val patientCommandMapper: PatientCommandMapper,
    private val registerPatientUseCase: RegisterPatientUseCase,
    private val updatePatientProfileUseCase: UpdatePatientProfileUseCase,
    private val changePatientEmailUseCase: ChangePatientEmailUseCase,
    private val deletePatientUseCase: DeletePatientUseCase
) {

    @GetMapping
    fun getPatients(patientSearchRequest: PatientSearchRequest): PageResponseDto<PatientResponse> {
        val searchCriteria: PatientSearchCriteria = patientSearchMapper.toPatientSearchCriteria(patientSearchRequest)
        val patients: PageResult<PatientResult> = patientSearchUseCase.search(searchCriteria)
        return patients.toPageResponse { patientSearchMapper.toPatientResponse(it) }
    }

    @PostMapping
    fun registerPatient(@Valid @RequestBody request: RegisterPatientRequest): PatientIdResponse {
        val command = patientCommandMapper.toRegisterPatientCommand(request)
        val patientId: Long = registerPatientUseCase.execute(command)
        return PatientIdResponse(patientId)
    }

    @PutMapping("/{id}")
    fun updatePatientProfile(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdatePatientProfileRequest
    ) {
        val command = patientCommandMapper.toUpdatePatientProfileCommand(id, request)
        updatePatientProfileUseCase.execute(command)
    }

    @PutMapping("/{id}/email")
    fun changePatientEmail(
        @PathVariable id: Long,
        @Valid @RequestBody request: ChangePatientEmailRequest
    ) {
        val command = patientCommandMapper.toChangePatientEmailCommand(id, request)
        changePatientEmailUseCase.execute(command)
    }

    @DeleteMapping("/{id}")
    fun deletePatient(@PathVariable id: Long) {
        deletePatientUseCase.execute(id)
    }
}