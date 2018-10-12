package mateuszmacholl.formica.converter.answer

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.answer.CreateAnswerDto
import mateuszmacholl.formica.model.answer.Answer
import mateuszmacholl.formica.service.post.PostService
import mateuszmacholl.formica.service.user.UserService
import org.springframework.stereotype.Service


@Service
class AnswerConverter(val userService: UserService, val postService: PostService) : DtoConverter<CreateAnswerDto>() {
    override fun toEntity(createAnswerDto: CreateAnswerDto): Answer {
        val user = userService.findByUsername(createAnswerDto.author) ?: throw IllegalArgumentException()
        val post = postService.findById(createAnswerDto.post as Int)
        if(!post.isPresent){
            throw IllegalArgumentException()
        }
        return Answer(
                content = createAnswerDto.content,
                post = post.get(),
                author = user
        )
    }
}