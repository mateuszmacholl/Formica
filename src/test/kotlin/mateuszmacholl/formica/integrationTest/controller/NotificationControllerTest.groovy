package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.model.notification.PostHasBeenCommentedNotification
import mateuszmacholl.formica.service.notification.NotificationService
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
class NotificationControllerTest extends Specification {
    @Autowired
    NotificationService notificationService
    @Autowired
    private TestRestTemplate restTemplate

    private String path = "/notifications/"

    def "get all notifications"() {
        when:
        def response = restTemplate.getForEntity(path, String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get notification by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity(path + id, PostHasBeenCommentedNotification.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete notification by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.exchange(path + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def notification = notificationService.findById(id)
        notification == Optional.empty()
    }
}