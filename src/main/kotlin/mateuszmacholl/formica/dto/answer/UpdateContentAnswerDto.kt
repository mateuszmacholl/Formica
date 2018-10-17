package mateuszmacholl.formica.dto.answer

import mateuszmacholl.formica.validation.filled.Filled

data class UpdateContentAnswerDto(
        @field:Filled
        val content: String
)