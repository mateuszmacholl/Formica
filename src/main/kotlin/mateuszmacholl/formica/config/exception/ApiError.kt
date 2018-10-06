package mateuszmacholl.formica.config.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ApiError {

    var status: HttpStatus? = null
    var message: String? = null
    val timestamp = LocalDateTime.now()
    var errors: List<String>? = null

    constructor(status: HttpStatus, message: String, errors: List<String>) : super() {
        this.status = status
        this.message = message
        this.errors = errors
    }

    constructor(status: HttpStatus, message: String, error: String) : super() {
        this.status = status
        this.message = message
        errors = listOf(error)
    }
}
