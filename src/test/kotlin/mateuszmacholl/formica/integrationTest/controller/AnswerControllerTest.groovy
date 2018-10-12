package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.model.answer.Answer
import mateuszmacholl.formica.model.comment.Comment
import mateuszmacholl.formica.service.answer.AnswerService
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
class AnswerControllerTest extends Specification {
    @Autowired
    AnswerService answerService
    @Autowired
    private TestRestTemplate restTemplate

    private String path = "/answers/"

    def "get all answers"() {
        when:
        def response = restTemplate.getForEntity(path, String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get answer by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity(path + id, Answer.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete answer by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.exchange(path + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def answer = answerService.findById(id)
        answer == Optional.empty()
    }

    def "add answer"() {
        given:
        def content = "content"
        def author = "d_enabled_user"
        def post = 1000
        def body = [
                content: content,
                author: author,
                post: post
        ]
        when:
        def response = restTemplate.postForEntity(path, body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        def answers = answerService.findAll() as ArrayList<Answer>
        answers.stream().filter { answer ->
            (
                    answer.content == content &&
                    answer.author.username == author &&
                    answer.post.id == post
            )
        } != Optional.empty()
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

}
