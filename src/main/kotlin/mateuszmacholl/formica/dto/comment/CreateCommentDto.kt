package mateuszmacholl.formica.dto.comment

import mateuszmacholl.formica.validation.answer.ExistAnswerWithId
import mateuszmacholl.formica.validation.filled.Filled
import mateuszmacholl.formica.validation.user.existUserWithUsername.ExistAccountWithUsername

data class CreateCommentDto(
        @field:Filled
        val content: String,

        @field:Filled
        @field:ExistAccountWithUsername
        val author: String,

        @field:ExistAnswerWithId
        val answer: Number
)