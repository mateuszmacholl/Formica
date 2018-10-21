package mateuszmacholl.formica.service.channel

import mateuszmacholl.formica.model.channel.Channel
import mateuszmacholl.formica.repo.ChannelRepo
import mateuszmacholl.formica.specification.ChannelSpec
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChannelService constructor(private val channelRepo: ChannelRepo) {

    fun findById(id: Int): Optional<Channel> {
        return channelRepo.findById(id)
    }

    fun findAll(channelSpec: ChannelSpec, pageable: Pageable): MutableIterable<Channel> {
        return channelRepo.findAll(channelSpec, pageable)
    }

    fun add(tag: Channel) {
        channelRepo.save(tag)
    }

    fun delete(tag: Channel) {
        channelRepo.delete(tag)
    }

    fun findByName(name: String): Channel? {
        return channelRepo.findByName(name)
    }
}
