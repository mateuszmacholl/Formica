package mateuszmacholl.formica.integrationTest.controller.user

import mateuszmacholl.formica.model.answer.Answer
import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.token.PasswordResetTokenService
import mateuszmacholl.formica.service.token.VerificationTokenService
import mateuszmacholl.formica.service.user.UserService
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

    def "get posts"(){
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity(path + id + '/posts', Post[].class)
        then:
        HttpStatus.OK == response.statusCode
        response.body != null
    }

    def "get answers"(){
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity(path + id + '/answers', Answer[].class)
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
