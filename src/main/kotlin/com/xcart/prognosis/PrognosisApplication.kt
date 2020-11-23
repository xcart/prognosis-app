package com.xcart.prognosis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*

@SpringBootApplication
@EnableCaching
@EnableScheduling
class PrognosisApplication

fun main(args: Array<String>) {
    // Temporary timezone fix
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    runApplication<PrognosisApplication>(*args)
}
