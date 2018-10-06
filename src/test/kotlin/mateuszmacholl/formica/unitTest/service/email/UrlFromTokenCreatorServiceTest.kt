package mateuszmacholl.formica.unitTest.service.email

import mateuszmacholl.formica.service.user.token.UrlFromTokenCreatorService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UrlFromTokenCreatorServiceTest {

    private var urlFromTokenCreatorService: UrlFromTokenCreatorService? = null

    @BeforeEach
    fun init() {
        urlFromTokenCreatorService = UrlFromTokenCreatorService()
    }

    @Test
    fun create_givesCorrectUrl() {
        //GIVEN
        val clientUrl = "clientUrl:0000/"
        val token = "exampleToken12345"
        val correctUrl = "$clientUrl?token=$token"
        //WHEN
        val createdUrl = urlFromTokenCreatorService!!.create(clientUrl, token)
        //THEN
        assertEquals(createdUrl, correctUrl)
    }

    @Test
    fun create_fromNull_throwIllegalArgumentException() {
        //THEN
        assertThrows(IllegalArgumentException::class.java) {
            //WHEN
            urlFromTokenCreatorService!!.create(null, null)
        }
    }
}
