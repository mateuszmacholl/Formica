package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.model.comment.Comment
import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.model.token.PasswordResetToken
import mateuszmacholl.formica.model.token.VerificationToken
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

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles(value = ["test"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest extends Specification {
    @Autowired
    UserService userService
    @Autowired
    PasswordResetTokenService passwordResetTokenService
    @Autowired
    VerificationTokenService verificationTokenService
    @Autowired
    private TestRestTemplate restTemplate

    private String path = "/users/"

    def "get all users"() {
        when:
        def response = restTemplate.getForEntity(path, String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get user by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity(path + id, User.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "add user"() {
        given:
        def username = "username"
        def email = "email@gmail.com"
        def password = "password"
        def url = "clientVerifyAccountUrl"

        Map body = [
                email   : email,
                username: username,
                password: password,
                url     : url
        ]

        when:
        def response = restTemplate.postForEntity(path, body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        def user = userService.findByUsername("username")
        user != null //created user
        username == user.username
        email == user.email
        password != user.password // different password (encoding)
        !user.enabled // user is not enabled
        user.verificationToken != null // created verification token
    }

    def "delete user by id"() {
        given:
        def id = 1000
        when:
        def response = restTemplate.exchange(path + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def task = userService.findById(id)
        task == Optional.empty()
    }

    def "get verification token"(){
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity(path + id + '/verification-tokens', VerificationToken.class)
        then:
        HttpStatus.OK == response.statusCode
        response.body != null
    }

    def "get password reset token"(){
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity(path + id + '/password-reset-tokens', PasswordResetToken.class)
        then:
        HttpStatus.OK == response.statusCode
        response.body != null
    }

    def "verification successful"() {
        given:
        def token = "token12345"

        when:
        def response = restTemplate.exchange(path + 'enabled?token=' + token, HttpMethod.PUT, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode
        User user = userService.findByUsername("d_fresh_user")
        user.enabled // enabled user
        user.getVerificationToken() == null //deleted token

    }

    def "send verification token successful"() {
        given:
        def email = "d_fresh_default@gmail.com"
        def clientUrl = "clientUrl"

        when:
        def response = restTemplate.postForEntity(path + 'verification-tokens?' +
                'email=' + email +
                '&url=' + clientUrl,
                null,
                String.class)

        then:
        HttpStatus.CREATED == response.statusCode
    }

    def "replace and send verification token if already one exist successful"() {
        given:
        def email = "d_fresh_default@gmail.com"
        def clientUrl = "clientUrl"
        def oldToken = new VerificationToken("token12345", userService.findByEmail(email))
        verificationTokenService.add(oldToken)

        when:
        def response = restTemplate.postForEntity(path + 'verification-tokens?' +
                'email=' + email +
                '&url=' + clientUrl,
                null,
                String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        User user = userService.findByEmail(email)
        user.verificationToken != null //created password reset token
        oldToken != user.verificationToken
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

    def "get posts"(){
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity(path + id + '/posts', Post[].class)
        then:
        HttpStatus.OK == response.statusCode
        response.body != null
    }

    def "get comments"(){
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity(path + id + '/comments', Comment[].class)
        then:
        HttpStatus.OK == response.statusCode
        response.body != null
    }

    def "get notifications"(){
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity(path + id + '/notifications', String.class)
        then:
        HttpStatus.OK == response.statusCode
    }
}
