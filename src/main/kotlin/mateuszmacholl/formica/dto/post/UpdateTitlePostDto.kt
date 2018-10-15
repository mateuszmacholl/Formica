package mateuszmacholl.formica.dto.post

import mateuszmacholl.formica.validation.filled.Filled

data class UpdateTitlePostDto(
        @field:Filled
        val title: String
)