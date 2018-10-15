package mateuszmacholl.formica.service.post

import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.repo.PostRepo
import mateuszmacholl.formica.specification.PostSpec
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostService
constructor(private val postRepo: PostRepo) {
    fun findById(id: Int): Optional<Post> {
        return postRepo.findById(id)
    }

    fun findAll(postSpec: PostSpec, pageable: Pageable): MutableIterable<Post> {
        return postRepo.findAll(postSpec, pageable)
    }

    fun add(post: Post) {
        postRepo.save(post)
    }

    fun delete(post: Post) {
        postRepo.delete(post)
    }
}