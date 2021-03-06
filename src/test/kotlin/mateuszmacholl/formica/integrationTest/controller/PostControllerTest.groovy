package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.model.answer.Answer
import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.service.post.PostService
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

    def "get all near area"() {
        given:
        def longitude = 18.2504
        def latitude = 54.61
        def range = 1000

        when:
        def response = restTemplate.getForEntity(path + "/near-area?longitude=" + longitude + "&latitude=" + latitude + "&range=" + range, Post[].class)

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

    def "add post with coordinates"() {
        given:
        def title = "title"
        def content = "content"
        def author = "d_enabled_user"
        def coordinates = [
                longitude: 10.4f,
                latitude: 10.4f
        ]
        def body = [
                title: title,
                content: content,
                author: author,
                coordinates: coordinates
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
                    post.author.username == author &&
                    post.coordinates.latitude == coordinates.latitude
            )
        } != Optional.empty()
    }

    def "add post with channel"() {
        given:
        def title = "title"
        def content = "content"
        def author = "d_enabled_user"
        def channel = 1000
        def body = [
                title: title,
                content: content,
                author: author,
                channel: channel
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
                            post.author.username == author &&
                            post.channel.id == channel
            )
        } != Optional.empty()
    }

    def "add post with channel and coordinates (BAD_REQUEST)"() {
        given:
        def title = "title"
        def content = "content"
        def author = "d_enabled_user"
        def channel = 1000
        def coordinates = [
                longitude: 10.4f,
                latitude: 10.4f
        ]
        def body = [
                title: title,
                content: content,
                author: author,
                channel: channel,
                coordinates: coordinates
        ]
        when:
        def response = restTemplate.postForEntity(path, body, String.class)

        then:
        HttpStatus.BAD_REQUEST == response.statusCode
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

    def "set tags"(){
        given:
        def id = 1000
        def tags = ["kotlin", "java"]
        def body = [
                tags: tags
        ]
        def oldPost = postService.findById(id).get()
        when:
        def response = restTemplate.exchange(path + id + '/tags', HttpMethod.PATCH, new HttpEntity(body), Post.class)
        then:
        HttpStatus.OK == response.statusCode
        def newPost = postService.findById(id)
        oldPost != newPost
        response.body != null
    }

    def "set title"(){
        given:
        def id = 1000
        def title = "new title"
        def body = [
                title: title
        ]
        def oldPost = postService.findById(id).get()
        when:
        def response = restTemplate.exchange(path + id + '/title', HttpMethod.PATCH, new HttpEntity(body), Post.class)
        then:
        HttpStatus.OK == response.statusCode
        def newPost = postService.findById(id)
        oldPost != newPost
        response.body != null
    }

    def "set content"(){
        given:
        def id = 1000
        def content = "new content"
        def body = [
                content: content
        ]
        def oldPost = postService.findById(id).get()
        when:
        def response = restTemplate.exchange(path + id + '/content', HttpMethod.PATCH, new HttpEntity(body), Post.class)
        then:
        HttpStatus.OK == response.statusCode
        def newPost = postService.findById(id)
        oldPost != newPost
        response.body != null
    }
}
