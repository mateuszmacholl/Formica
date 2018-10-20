package mateuszmacholl.formica.dto.comment

import mateuszmacholl.formica.validation.filled.Filled

data class UpdateContentCommentDto(
        @field:Filled
        val content: String
)