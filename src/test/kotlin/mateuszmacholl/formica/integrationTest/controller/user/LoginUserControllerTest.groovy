package mateuszmacholl.formica.integrationTest.controller.user

import mateuszmacholl.formica.model.token.PasswordResetToken
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.token.PasswordResetTokenService
import mateuszmacholl.formica.service.token.VerificationTokenService
import mateuszmacholl.formica.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles(value = ["test"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginUserControllerTest extends Specification {
    @Autowired
    UserService userService
    @Autowired
    PasswordResetTokenService passwordResetTokenService
    @Autowired
    VerificationTokenService verificationTokenService
    @Autowired
    private TestRestTemplate restTemplate

    private String path = "/users/"

    def "get password reset token"(){
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity(path + id + '/password-reset-tokens', PasswordResetToken.class)
        then:
        HttpStatus.OK == response.statusCode
        response.body != null
    }

    def "change password successful"() {
        given:
        def token = "token12345"
        def newPassword = "newPassword"
        def oldPassword = passwordResetTokenService.findByToken(token).user.password

        Map body = [
                newPassword: newPassword
        ]

        when:
        def response = restTemplate.exchange(path + 'password?token=' + token, HttpMethod.PUT, new HttpEntity(body), String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode
        oldPassword != newPassword // changed password
        passwordResetTokenService.findByToken(token) == null // deleted password reset token
    }

    def "send reset password token successful"() {
        given:
        def email = "d_fresh_default@gmail.com"
        def clientUrl = "clientUrl"

        when:
        def response = restTemplate.postForEntity(path + 'password-reset-tokens?' +
                'email=' + email +
                '&url=' + clientUrl,
                null,
                String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        User user = userService.findByEmail(email)
        user.passwordResetToken != null //created password reset token
    }

    def "replace and send reset password token if already one exist successful"() {
        given:
        def email = "d_fresh_default@gmail.com"
        def clientUrl = "clientUrl"
        def oldToken = new PasswordResetToken("token12345", userService.findByEmail(email))
        passwordResetTokenService.add(oldToken)

        when:
        def response = restTemplate.postForEntity(path + 'password-reset-tokens?' +
                'email=' + email +
                '&url=' + clientUrl,
                null,
                String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        User user = userService.findByEmail(email)
        user.passwordResetToken != null //created password reset token
        oldToken != user.passwordResetToken
    }
}
