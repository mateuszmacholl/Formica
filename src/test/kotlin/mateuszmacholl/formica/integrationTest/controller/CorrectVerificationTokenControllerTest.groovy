package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.model.user.VerificationToken
import mateuszmacholl.formica.service.user.token.VerificationTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles(value = ["test"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CorrectVerificationTokenControllerTest extends Specification {
    @Autowired
    VerificationTokenService verificationTokenService
    @Autowired
    private TestRestTemplate restTemplate

    def "get all verification tokens"() {
        when:
        def response = restTemplate.getForEntity('/verification-tokens', String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get verification token by id"() {
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity('/verification-tokens/' + id, VerificationToken.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete verification token by id"() {
        given:
        def id = 1000
        when:
        def response = restTemplate.exchange('/verification-tokens/' + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def verificationToken = verificationTokenService.findById(id)
        verificationToken == Optional.empty()
    }

    def "add verification token"() {
        given:
        def token = "token123456789"
        def user = 1000
        def body = [
                token: token,
                user : user,
        ]
        when:
        def response = restTemplate.postForEntity('/verification-tokens', body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        def verificationTokens = verificationTokenService.findAll() as ArrayList<VerificationToken>
        verificationTokens.stream().filter { verificationToken ->
            (
                    verificationToken.token == token
                            && verificationToken.user.id == user
            )
        } != Optional.empty()
    }

}
