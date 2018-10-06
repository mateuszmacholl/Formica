package mateuszmacholl.formica.unitTest.service.passwordEncoder

import mateuszmacholl.formica.service.user.PasswordEncoderService
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

internal class PasswordEncoderServiceTest {

    private var passwordEncoderService: PasswordEncoderService? = null

    @BeforeEach
    fun init() {
        passwordEncoderService = PasswordEncoderService(BCryptPasswordEncoder())
    }

    @Test
    fun encodePassword_returnDifferentPassword() {
        val encodedPassword = passwordEncoderService!!.encodePassword(PASSWORD)

        assertNotEquals(PASSWORD, encodedPassword)
    }

    companion object {
        private const val PASSWORD = "t_password"
    }
}
