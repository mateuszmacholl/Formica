package mateuszmacholl.formica.repo

import mateuszmacholl.formica.model.post.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface PostRepo: JpaRepository<Post, Int>, JpaSpecificationExecutor<Post>{
    fun findAllByOrderByCreationDateDesc(): List<Post>
}