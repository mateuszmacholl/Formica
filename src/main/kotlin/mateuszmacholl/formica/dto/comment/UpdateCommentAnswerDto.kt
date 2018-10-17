package mateuszmacholl.formica.dto.comment

import mateuszmacholl.formica.validation.filled.Filled

data class UpdateCommentAnswerDto(
        @field:Filled
        val content: String
)