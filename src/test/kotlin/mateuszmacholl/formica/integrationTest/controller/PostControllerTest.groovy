package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.model.answer.Answer
import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.service.post.PostService
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
class PostControllerTest extends Specification {
    @Autowired
    PostService postService
    @Autowired
    private TestRestTemplate restTemplate

    private String path = "/posts/"

    def "get all posts"() {
        when:
        def response = restTemplate.getForEntity(path, String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get post by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity(path + id, Post.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete post by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.exchange(path + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def post = postService.findById(id)
        post == Optional.empty()
    }

    def "add post"() {
        given:
        def title = "title"
        def content = "content"
        def author = "d_enabled_user"
        def body = [
                title: title,
                content: content,
                author: author
        ]
        when:
        def response = restTemplate.postForEntity(path, body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        def posts = postService.findAll() as ArrayList<Post>
        posts.stream().filter { post ->
            (
                    post.title == title &&
                    post.content == content &&
                    post.author.username == author
            )
        } != Optional.empty()
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

    /*
    def "get best answer"(){
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity(path + id + '/best-answer', Answer.class)
        then:
        HttpStatus.OK == response.statusCode
        response.body != null
    }
    */
}
