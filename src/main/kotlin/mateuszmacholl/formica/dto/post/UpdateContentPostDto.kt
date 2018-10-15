package mateuszmacholl.formica.dto.post

import mateuszmacholl.formica.validation.filled.Filled

data class UpdateContentPostDto(
        @field:Filled
        val content: String
)