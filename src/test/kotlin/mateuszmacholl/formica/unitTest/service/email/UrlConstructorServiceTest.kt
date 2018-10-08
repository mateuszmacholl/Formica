package mateuszmacholl.formica.unitTest.service.email

import mateuszmacholl.formica.service.url.UrlConstructorService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UrlConstructorServiceTest {

    private lateinit var urlConstructorService: UrlConstructorService

    @BeforeEach
    fun init() {
        urlConstructorService = UrlConstructorService()
    }

    @Test
    fun create_givesCorrectUrl() {
        //GIVEN
        val clientUrl = "clientUrl:0000/"
        val token = "exampleToken12345"
        val correctUrl = "$clientUrl?token=$token"
        //WHEN
        val createdUrl = urlConstructorService.constructWithToken(clientUrl, token)
        //THEN
        assertEquals(createdUrl, correctUrl)
    }
}
