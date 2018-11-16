package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.model.token.VerificationToken
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.token.VerificationTokenService
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


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles(value = ["test"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VerificationTokenControllerTest extends Specification {
    @Autowired
    VerificationTokenService verificationTokenService
    @Autowired
    private TestRestTemplate restTemplate

    private String path = "/verification-tokens/"

    def "get all verification tokens"() {
        when:
        def response = restTemplate.getForEntity(path, String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get verification token by id"() {
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity(path + id, VerificationToken.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get user"(){
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity(path + id + '/users', User.class)
        then:
        HttpStatus.OK == response.statusCode
        response.body != null
    }

    def "delete verification token by id"() {
        given:
        def id = 1000
        when:
        def response = restTemplate.exchange(path + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def verificationToken = verificationTokenService.findById(id)
        verificationToken == Optional.empty()
    }

    def "add verification token"() {
        given:
        def token = "token123456789"
        def user = "d_enabled_user"
        def body = [
                token: token,
                user : user,
        ]
        when:
        def response = restTemplate.postForEntity(path, body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        def verificationTokens = verificationTokenService.findAll() as ArrayList<VerificationToken>
        verificationTokens.stream().filter { verificationToken ->
            (
                    verificationToken.token == token
                            && verificationToken.user.username == user
            )
        } != Optional.empty()
    }
}
