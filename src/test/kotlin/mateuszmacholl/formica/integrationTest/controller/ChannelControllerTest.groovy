package mateuszmacholl.formica.integrationTest.controller

import mateuszmacholl.formica.model.channel.Channel
import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.service.channel.ChannelService
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
class ChannelControllerTest extends Specification {
    @Autowired
    ChannelService channelService
    @Autowired
    private TestRestTemplate restTemplate

    private String path = "/channels/"

    def "get all channels"() {
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
        def response = restTemplate.getForEntity(path + "/near-area?longitude=" + longitude + "&latitude=" + latitude + "&range=" + range, Channel[].class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get channel by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity(path + id, Channel.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete channel by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.exchange(path + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def channel = channelService.findById(id)
        channel == Optional.empty()
    }

    def "add channel"() {
        given:
        def name = "dworzec glowny gdansk"
        def coordinates = [
                latitude : 10f,
                longitude: 10f
        ]
        def body = [
                name       : name,
                coordinates: coordinates
        ]
        when:
        def response = restTemplate.postForEntity(path, body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        def channels = channelService.findAll() as ArrayList<Channel>
        channels.stream().filter { channel ->
            (
                    channel.name == name &&
                            channel.coordinates.longitude == coordinates.longitude
            )
        } != Optional.empty()
    }

    def "get posts"() {
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity(path + id + '/posts', Post[].class)
        then:
        HttpStatus.OK == response.statusCode
        response.body != null
    }

    def "set name"() {
        given:
        def id = 1000
        def name = "new name"
        def body = [
                name: name
        ]
        def oldChannel = channelService.findById(id).get()
        when:
        def response = restTemplate.exchange(path + id + '/name', HttpMethod.PATCH, new HttpEntity(body), Channel.class)
        then:
        HttpStatus.OK == response.statusCode
        def newChannel = channelService.findById(id)
        oldChannel != newChannel
        response.body != null
    }

}
