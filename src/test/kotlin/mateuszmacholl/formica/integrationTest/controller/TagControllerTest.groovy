package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.model.tag.Tag
import mateuszmacholl.formica.service.tag.TagService
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
class TagControllerTest extends Specification {
    @Autowired
    TagService tagService
    @Autowired
    private TestRestTemplate restTemplate

    private String path = "/tags/"

    def "get all tags"() {
        when:
        def response = restTemplate.getForEntity(path, String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get tag by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity(path + id, Tag.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete tag by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.exchange(path + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def tag = tagService.findById(id)
        tag == Optional.empty()
    }

    def "add tag"() {
        given:
        def name = "c++"
        def body = [
                name: name
        ]
        when:
        def response = restTemplate.postForEntity(path, body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        def tags = tagService.findAll() as ArrayList<Tag>
        tags.stream().filter { tag ->
            (
                    tag.name == name
            )
        } != Optional.empty()
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

}
