package ru.vsu.cs.tp.freelanceFinderServer

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "FreelanceFinderServer API", version = "1.0"))
class FreelanceFinderServerApplication

fun main(args: Array<String>) {
	runApplication<FreelanceFinderServerApplication>(*args)
}
