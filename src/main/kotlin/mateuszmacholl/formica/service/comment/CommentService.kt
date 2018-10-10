package mateuszmacholl.formica.service.comment

import mateuszmacholl.formica.model.comment.Comment
import mateuszmacholl.formica.repo.CommentRepo
import mateuszmacholl.formica.specification.CommentSpec
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CommentService
constructor(private val commentRepo: CommentRepo) {
    fun findById(id: Int): Optional<Comment> {
        return commentRepo.findById(id)
    }

    fun findAll(commentSpec: CommentSpec, pageable: Pageable): MutableIterable<Comment> {
        return commentRepo.findAll(commentSpec, pageable)
    }

    fun add(tag: Comment) {
        commentRepo.save(tag)
    }

    fun delete(tag: Comment) {
        commentRepo.delete(tag)
    }
}