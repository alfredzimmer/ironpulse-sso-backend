package net.ironpulse.ironpulsessobackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IronpulseSsoBackendApplication

fun main(args: Array<String>) {
    runApplication<IronpulseSsoBackendApplication>(*args)
}
