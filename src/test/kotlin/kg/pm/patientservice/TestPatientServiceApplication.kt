package kg.pm.patientservice

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<PatientServiceApplication>().with(TestcontainersConfiguration::class).run(*args)
}
