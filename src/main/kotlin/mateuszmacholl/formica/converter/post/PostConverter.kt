package mateuszmacholl.formica.converter.post

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.post.CreatePostDto
import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.service.user.UserService
import org.springframework.stereotype.Service

@Service
class PostConverter(val userService: UserService) : DtoConverter<CreatePostDto>() {

    override fun toEntity(createPostDto: CreatePostDto): Post {
        val user = userService.findByUsername(createPostDto.author) ?: throw IllegalArgumentException()
        return Post(
                title = createPostDto.title,
                content = createPostDto.content,
                author = user
        )
    }
}