package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.model.comment.Comment
import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.service.comment.CommentService
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
class CommentControllerTest extends Specification {
    @Autowired
    CommentService commentService
    @Autowired
    private TestRestTemplate restTemplate

    private String path = "/comments/"

    def "get all comments"() {
        when:
        def response = restTemplate.getForEntity(path, String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get comment by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity(path + id, Comment.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete comment by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.exchange(path + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def comment = commentService.findById(id)
        comment == Optional.empty()
    }

    def "add comment"() {
        given:
        def content = "content"
        def author = "d_enabled_user"
        def answer = 1000
        def body = [
                content: content,
                author: author,
                answer: answer
        ]
        when:
        def response = restTemplate.postForEntity(path, body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        def comments = commentService.findAll() as ArrayList<Comment>
        comments.stream().filter { comment ->
            (
                    comment.content == content &&
                    comment.author.username == author &&
                    comment.answer.id == answer
            )
        } != Optional.empty()
    }

    def "set content"(){
        given:
        def id = 1000
        def content = "new content"
        def body = [
                content: content
        ]
        def oldComment = commentService.findById(id).get()
        when:
        def response = restTemplate.exchange(path + id + '/content', HttpMethod.PATCH, new HttpEntity(body), Post.class)
        then:
        HttpStatus.OK == response.statusCode
        def newComment = commentService.findById(id)
        oldComment != newComment
        response.body != null
    }

}
