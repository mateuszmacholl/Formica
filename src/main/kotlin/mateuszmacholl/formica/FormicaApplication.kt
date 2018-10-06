package mateuszmacholl.formica

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class FormicaApplication

fun main(args: Array<String>) {
    runApplication<FormicaApplication>(*args)
}
