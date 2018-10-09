package mateuszmacholl.formica.dto.post

import mateuszmacholl.formica.validation.filled.Filled
import mateuszmacholl.formica.validation.user.existUserWithUsername.ExistAccountWithUsername

data class CreatePostDto(
        @field:Filled
        val title: String,

        @field:Filled
        val content: String,

        @field:Filled
        @field:ExistAccountWithUsername
        val author: String
)