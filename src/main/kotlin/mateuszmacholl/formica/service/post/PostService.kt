package mateuszmacholl.formica.service.post

import mateuszmacholl.formica.model.coordinates.Coordinates
import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.repo.PostRepo
import mateuszmacholl.formica.service.coordinates.CoordinatesCalculatorService
import mateuszmacholl.formica.specification.PostSpec
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostService
constructor(private val postRepo: PostRepo,
            private val coordinatesCalculatorService: CoordinatesCalculatorService) {
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

    fun findNearArea(coordinates: Coordinates, range: Int): List<Post> {
        val posts = postRepo.findAll()
        val postsNearArea = mutableListOf<Post>()
        posts.forEach {
            val postCoordinates: Coordinates = if(it.channel == null){
                it.coordinates!!
            } else {
                it.channel!!.coordinates!!
            }
            if(coordinatesCalculatorService.calcDistance(postCoordinates, coordinates) <= range){
                postsNearArea.add(it)
            }
        }
        return postsNearArea
    }
}