package mateuszmacholl.formica.converter.comment

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.comment.CreateCommentDto
import mateuszmacholl.formica.model.comment.Comment
import mateuszmacholl.formica.service.post.PostService
import mateuszmacholl.formica.service.user.UserService
import org.springframework.stereotype.Service

@Service
class CommentConverter(val userService: UserService, val postService: PostService) : DtoConverter<CreateCommentDto>() {
    override fun toEntity(createCommentDto: CreateCommentDto): Comment {
        val user = userService.findByUsername(createCommentDto.author) ?: throw IllegalArgumentException()
        val post = postService.findById(createCommentDto.post as Int)
        if(!post.isPresent){
            throw IllegalArgumentException()
        }
        return Comment(
                content = createCommentDto.content,
                post = post.get(),
                author = user
        )
    }
}