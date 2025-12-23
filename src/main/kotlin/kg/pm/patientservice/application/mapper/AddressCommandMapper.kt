package kg.pm.patientservice.application.mapper

import kg.pm.patientservice.application.dto.patient.AddressCommand
import kg.pm.patientservice.domain.core.model.valueobject.Address
import org.springframework.stereotype.Component

@Component
class AddressCommandMapper {

    fun toDomain(addressCommand: AddressCommand): Address {
        return Address(
            street = addressCommand.street,
            city = addressCommand.city,
            state = addressCommand.state,
            postalCode = addressCommand.postalCode,
            country = addressCommand.country
        )
    }
}