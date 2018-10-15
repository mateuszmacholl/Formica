package mateuszmacholl.formica.dto.post

import mateuszmacholl.formica.validation.tag.existTagWithId.ExistTagsWithName

data class UpdateTagsPostDto(
        @field:ExistTagsWithName
        val tags: Set<String>
)