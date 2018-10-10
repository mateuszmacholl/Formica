package mateuszmacholl.formica.repo

import mateuszmacholl.formica.model.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface CommentRepo : JpaRepository<Comment, Int>, JpaSpecificationExecutor<Comment> {
}