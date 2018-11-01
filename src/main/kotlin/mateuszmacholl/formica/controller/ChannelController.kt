package mateuszmacholl.formica.controller

import mateuszmacholl.formica.converter.channel.ChannelConverter
import mateuszmacholl.formica.dto.channel.CreateChannelDto
import mateuszmacholl.formica.dto.channel.UpdateNameChannelDto
import mateuszmacholl.formica.model.coordinates.Coordinates
import mateuszmacholl.formica.service.channel.ChannelService
import mateuszmacholl.formica.specification.ChannelSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/channels"])
class ChannelController {
    @Autowired
    lateinit var channelService: ChannelService
    @Autowired
    lateinit var channelConverter: ChannelConverter

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(channelSpec: ChannelSpec, pageable: Pageable): ResponseEntity<*> {
        val channels = channelService.findAll(channelSpec, pageable)
        return ResponseEntity(channels, HttpStatus.OK)
    }

    @RequestMapping(value = ["/near-area"], method = [RequestMethod.GET])
    fun getAllNearArea(@RequestParam(value = "longitude") longitude: Float,
                       @RequestParam(value = "latitude") latitude: Float,
                       @RequestParam(value = "range") range: Int): ResponseEntity<*> {
        val coordinates = Coordinates(latitude = latitude, longitude =  longitude)
        val channels = channelService.findNearArea(coordinates, range)
        return ResponseEntity(channels, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val channel = channelService.findById(id)
        return if (!channel.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(channel.get(), HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createChannelDto: CreateChannelDto): ResponseEntity<*> {
        val channel = channelConverter.toEntity(createChannelDto)
        channelService.add(channel)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val channel = channelService.findById(id)
        return if (!channel.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            channelService.delete(channel.get())
            ResponseEntity<Any>(HttpStatus.NO_CONTENT )
        }
    }

    @RequestMapping(value = ["/{id}/posts"], method = [RequestMethod.GET])
    fun getPosts(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val channel = channelService.findById(id)
        return if (!channel.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val posts = channel.get().posts
            ResponseEntity<Any>(posts, HttpStatus.OK )
        }
    }

    @RequestMapping(value = ["/{id}/name"], method = [RequestMethod.PATCH], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setName(@PathVariable(value = "id") id: Int,
                 @RequestBody @Validated updateNameChannelDto: UpdateNameChannelDto): ResponseEntity<*> {
        val channel = channelService.findById(id)
        return if (!channel.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val updatedChannel = channel.get()
            updatedChannel.name = updateNameChannelDto.name
            channelService.add(updatedChannel)
            ResponseEntity<Any>(updatedChannel, HttpStatus.OK )
        }
    }
}