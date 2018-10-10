package mateuszmacholl.formica.dto.comment

import mateuszmacholl.formica.validation.filled.Filled
import mateuszmacholl.formica.validation.post.ExistPostWithId
import mateuszmacholl.formica.validation.user.existUserWithUsername.ExistAccountWithUsername

data class CreateCommentDto(
        @field:Filled
        val content: String,

        @field:Filled
        @field:ExistAccountWithUsername
        val author: String,

        @field:ExistPostWithId
        val post: Number
)