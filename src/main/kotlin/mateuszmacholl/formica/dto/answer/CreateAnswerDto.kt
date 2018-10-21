package mateuszmacholl.formica.dto.answer

import mateuszmacholl.formica.validation.filled.Filled
import mateuszmacholl.formica.validation.post.existPostWithId.ExistPostWithId
import mateuszmacholl.formica.validation.user.existUserWithUsername.ExistAccountWithUsername

data class CreateAnswerDto(
        @field:Filled
        val content: String,

        @field:Filled
        @field:ExistAccountWithUsername
        val author: String,

        @field:ExistPostWithId
        val post: Number
)