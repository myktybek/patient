package kg.pm.patientservice.infrastructure.jpa.base

import java.time.LocalDateTime

abstract class AuditMetadata {
    var createdBy: Long? = null
    var createdAt: LocalDateTime? = null
    var lastModifiedBy: Long? = null
    var lastModifiedDate: LocalDateTime? = null
}