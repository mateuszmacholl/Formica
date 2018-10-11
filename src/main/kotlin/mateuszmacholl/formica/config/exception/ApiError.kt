package mateuszmacholl.formica.config.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ApiError(
        val status: HttpStatus,
        val message: String
) {
    val timestamp = LocalDateTime.now()
    var errors: List<String> = listOf()

    constructor(status: HttpStatus, message: String, error: String) : this(status, message) {
        this.errors = listOf(error)
    }
    constructor(status: HttpStatus, message: String, errors: List<String>) : this(status, message) {
        this.errors = errors
    }
}
