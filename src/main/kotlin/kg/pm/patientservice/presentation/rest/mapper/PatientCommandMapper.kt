package kg.pm.patientservice.presentation.rest.mapper

import kg.pm.patientservice.application.dto.patient.AddressCommand
import kg.pm.patientservice.application.dto.patient.ChangePatientEmailCommand
import kg.pm.patientservice.application.dto.patient.RegisterPatientCommand
import kg.pm.patientservice.application.dto.patient.UpdatePatientProfileCommand
import kg.pm.patientservice.presentation.rest.dto.AddressRequest
import kg.pm.patientservice.presentation.rest.dto.ChangePatientEmailRequest
import kg.pm.patientservice.presentation.rest.dto.RegisterPatientRequest
import kg.pm.patientservice.presentation.rest.dto.UpdatePatientProfileRequest
import org.springframework.stereotype.Component

/**
 * Mapper for converting REST request DTOs to application command DTOs.
 * Handles the presentation-to-application layer boundary.
 */
@Component
class PatientCommandMapper {

    fun toRegisterPatientCommand(request: RegisterPatientRequest): RegisterPatientCommand {
        return RegisterPatientCommand(
            email = request.email,
            name = request.name,
            address = request.address?.toAddressCommand(),
            dateOfBirth = request.dateOfBirth
        )
    }

    fun toUpdatePatientProfileCommand(patientId: Long, request: UpdatePatientProfileRequest): UpdatePatientProfileCommand {
        return UpdatePatientProfileCommand(
            patientId = patientId,
            name = request.name,
            address = request.address?.toAddressCommand(),
            dateOfBirth = request.dateOfBirth
        )
    }

    fun toChangePatientEmailCommand(patientId: Long, request: ChangePatientEmailRequest): ChangePatientEmailCommand {
        return ChangePatientEmailCommand(
            patientId = patientId,
            newEmail = request.newEmail
        )
    }

    private fun AddressRequest.toAddressCommand(): AddressCommand {
        return AddressCommand(
            street = this.street,
            city = this.city,
            state = this.state,
            postalCode = this.postalCode,
            country = this.country
        )
    }
}
