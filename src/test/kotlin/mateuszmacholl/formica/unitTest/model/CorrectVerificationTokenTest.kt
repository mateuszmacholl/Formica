package mateuszmacholl.formica.unitTest.model

import mateuszmacholl.formica.model.token.VerificationToken
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class CorrectVerificationTokenTest {
    private lateinit var verificationToken: VerificationToken

    @BeforeEach
    fun init() {
        verificationToken = VerificationToken()
    }

    private fun getActualDateEditedByMinutes(minutes: Int): Date {
        val date = Calendar.getInstance()
        date.add(Calendar.MINUTE, minutes)
        return date.time
    }

    @Test
    fun isExpired_true() {
        verificationToken.expirationDate = getActualDateEditedByMinutes(-1)
        assertTrue(verificationToken.hasExpired())
    }

    @Test
    fun isExpired_false() {
        verificationToken.expirationDate = getActualDateEditedByMinutes(1)
        assertFalse(verificationToken.hasExpired())
    }

    @Test
    fun hasSetExpirationDate() {
        assertNotNull(verificationToken.expirationDate)
    }
}
