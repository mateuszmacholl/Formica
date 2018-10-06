package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.integrationTest.helper.JwtHelper
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
@ActiveProfiles(value = ["development"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtTest extends Specification {
    @Autowired
    private TestRestTemplate restTemplate

    def "login successful"() {
        when:
        def response = JwtHelper.performLogin(restTemplate)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "login with wrong password"() {
        given:
        def username = 'd_enabled_user'
        Map body = [
                username: username,
                password: 'wrong'
        ]
        when:
        def response =  restTemplate.postForEntity('/auth/login', body, String.class)

        then:
        HttpStatus.UNAUTHORIZED == response.statusCode
    }

    def "login with wrong username"() {
        given:
        def password = 'root'
        Map body = [
                username: 'wrong',
                password: password
        ]
        when:
        def response =  restTemplate.postForEntity('/auth/login', body, String.class)

        then:
        HttpStatus.UNAUTHORIZED == response.statusCode
    }



    def "get access to secured endpoint by user"() {
        given:
        def userId = 1000
        def loginResponse = JwtHelper.performLogin(restTemplate)
        def header = JwtHelper.getAuthHeader(loginResponse)
        HttpEntity<?> httpEntity = new HttpEntity<Object>(header)

        when:
        def response = restTemplate.exchange('/users/' + userId, HttpMethod.DELETE, httpEntity, String.class)
        then:
        HttpStatus.NO_CONTENT == response.statusCode
    }

    def "get forbidden status code for secured endpoint when without token"(){
        given:
        def userId = 1000

        when:
        def response = restTemplate.exchange('/users/' + userId, HttpMethod.DELETE, null, String.class)
        then:
        HttpStatus.FORBIDDEN == response.statusCode
    }
}
