package mateuszmacholl.formica.repo

import mateuszmacholl.formica.model.channel.Channel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ChannelRepo : JpaRepository<Channel, Int>, JpaSpecificationExecutor<Channel> {
    fun findByName(@Param("name") name: String): Channel?
}
