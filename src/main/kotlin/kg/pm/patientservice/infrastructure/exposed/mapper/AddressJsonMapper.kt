package kg.pm.patientservice.infrastructure.exposed.mapper

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kg.pm.patientservice.domain.core.model.valueobject.Address

/**
 * Mapper for serializing/deserializing Address value object to/from JSON.
 * Used for storing structured addresses in the database.
 */
object AddressJsonMapper {
    private val objectMapper = jacksonObjectMapper()

    /**
     * Serializes an Address to JSON string.
     */
    fun toJson(address: Address): String {
        return objectMapper.writeValueAsString(
            mapOf(
                "street" to address.street,
                "city" to address.city,
                "state" to address.state,
                "postalCode" to address.postalCode,
                "country" to address.country
            )
        )
    }

    /**
     * Deserializes JSON string to Address value object.
     * Returns null if the JSON is null or empty.
     * Throws exception if JSON is invalid.
     */
    fun fromJson(json: String?): Address? {
        if (json.isNullOrBlank()) return null

        return try {
            val map: Map<String, String> = objectMapper.readValue(json)
            Address(
                street = map["street"] ?: throw IllegalArgumentException("Missing 'street' in address JSON"),
                city = map["city"] ?: throw IllegalArgumentException("Missing 'city' in address JSON"),
                state = map["state"] ?: throw IllegalArgumentException("Missing 'state' in address JSON"),
                postalCode = map["postalCode"] ?: throw IllegalArgumentException("Missing 'postalCode' in address JSON"),
                country = map["country"] ?: throw IllegalArgumentException("Missing 'country' in address JSON")
            )
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid address JSON: $json", e)
        }
    }
}
