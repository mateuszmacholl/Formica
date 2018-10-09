package mateuszmacholl.formica.service.tag

import mateuszmacholl.formica.model.tag.Tag
import mateuszmacholl.formica.repo.TagRepo
import mateuszmacholl.formica.specification.TagSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class TagService @Autowired
constructor(private val tagRepo: TagRepo){
    fun findById(id: Int): Optional<Tag> {
        return tagRepo.findById(id)
    }

    fun findAll(tagSpec: TagSpec, pageable: Pageable): MutableIterable<Tag> {
        return tagRepo.findAll(tagSpec, pageable)
    }

    fun add(tag: Tag) {
        tagRepo.save(tag)
    }

    fun delete(tag: Tag) {
        tagRepo.delete(tag)
    }

    fun findByName(name: String): Tag? {
        return tagRepo.findByName(name)
    }
}