package mateuszmacholl.formica.unitTest.model

import mateuszmacholl.formica.model.user.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UserTest {
    private lateinit var user: User

    @BeforeEach
    fun init() {
        user = User()
    }

    @Test
    fun constructor_defaultDataIsSet() {
        assertEquals(user.enabled, false)
        assertEquals(user.roles[0], "user")
        assertNotNull(user.creationDate)
    }
}
