package mateuszmacholl.formica.converter.comment

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.comment.CreateCommentDto
import mateuszmacholl.formica.model.comment.Comment
import mateuszmacholl.formica.service.answer.AnswerService
import mateuszmacholl.formica.service.user.UserService
import org.springframework.stereotype.Service

@Service
class CommentConverter(val userService: UserService, val answerService: AnswerService) : DtoConverter<CreateCommentDto>() {
    override fun toEntity(from: CreateCommentDto): Comment {
        val user = userService.findByUsername(from.author) ?: throw IllegalArgumentException()
        val answer = answerService.findById(from.answer as Int)
        if(!answer.isPresent){
            throw IllegalArgumentException()
        }
        return Comment(
                content = from.content,
                answer = answer.get(),
                author = user
        )
    }
}