package mateuszmacholl.formica.repo

import mateuszmacholl.formica.model.tag.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TagRepo : JpaRepository<Tag, Int>, JpaSpecificationExecutor<Tag> {
    fun findByName(@Param("name") name: String): Tag?
}