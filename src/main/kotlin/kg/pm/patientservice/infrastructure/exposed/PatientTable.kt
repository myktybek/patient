package kg.pm.patientservice.infrastructure.exposed

import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.javatime.date

/**
 * Exposed table definition matching the SQL migration schema.
 * Table name matches SQL: "patient" (singular)
 * Note: SQL uses TIMESTAMP for registered_date, but we'll convert to LocalDate in mapping
 */
object PatientsTable : LongIdTable("patient") {
    val name = varchar("name", length = 255).nullable()
    val email = varchar("email", length = 255).uniqueIndex()
    val address = varchar("address", length = 400).nullable()
    val dateOfBirth = date("date_of_birth").nullable()
    val registeredDate = date("registered_date") // SQL uses TIMESTAMP WITHOUT TIME ZONE NOT NULL
    
    // Audit fields are commented out in SQL migration, so not included here
    // Uncomment if you enable audit fields in SQL:
    // val createdBy = long("created_by").nullable()
    // val createdAt = timestamp("created_at").nullable()
    // val lastModifiedBy = long("last_modified_by").nullable()
    // val lastModifiedDate = timestamp("last_modified_date").nullable()
}