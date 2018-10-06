package mateuszmacholl.formica.unitTest.model

import mateuszmacholl.formica.model.user.PasswordResetToken
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class PasswordResetTokenTest {
    private var passwordResetToken: PasswordResetToken? = null

    @BeforeEach
    fun init() {
        passwordResetToken = PasswordResetToken()
    }

    private fun getActualDateEditedByMinutes(minutes: Int): Date {
        val date = Calendar.getInstance()
        date.add(Calendar.MINUTE, minutes)
        return date.time
    }

    @Test
    fun isExpired_true() {
        passwordResetToken!!.expirationDate = getActualDateEditedByMinutes(-1)
        assertTrue(passwordResetToken!!.hasExpired())
    }

    @Test
    fun isExpired_false() {
        passwordResetToken!!.expirationDate = getActualDateEditedByMinutes(1)
        assertFalse(passwordResetToken!!.hasExpired())
    }

    @Test
    fun hasSetExpirationDate() {
        assertNotNull(passwordResetToken!!.expirationDate)
    }
}
