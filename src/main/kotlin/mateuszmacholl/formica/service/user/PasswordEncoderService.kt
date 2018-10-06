package mateuszmacholl.formica.service.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordEncoderService(private val passwordEncoder: PasswordEncoder) {

    fun encodePassword(password: String): String {
        return passwordEncoder.encode(password)
    }
}
