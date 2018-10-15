package mateuszmacholl.formica.dto.post

import mateuszmacholl.formica.validation.answer.ExistAnswerWithId

data class UpdateBestAnswerPostDto(
        @field:ExistAnswerWithId
        val bestAnswer: Int
)