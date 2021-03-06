package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.model.token.PasswordResetToken
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.token.PasswordResetTokenService
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
class PasswordResetTokenControllerTest extends Specification {
    @Autowired
    PasswordResetTokenService passwordResetTokenService
    @Autowired
    private TestRestTemplate restTemplate

    private String path = "/password-reset-tokens/"

    def "get all password reset tokens"() {
        when:
        def response = restTemplate.getForEntity(path, String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get password reset token by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity(path + id, PasswordResetToken.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete password reset token by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.exchange(path + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def passwordResetToken = passwordResetTokenService.findById(id)
        passwordResetToken == Optional.empty()
    }

    def "add password reset token"() {
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

        def passwordResetTokens = passwordResetTokenService.findAll() as ArrayList<PasswordResetToken>
        passwordResetTokens.stream().filter { passwordResetToken ->
            (
                    passwordResetToken.token == token
                            && passwordResetToken.user.username == user
            )
        } != Optional.empty()
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

}
