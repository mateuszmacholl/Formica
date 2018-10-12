package mateuszmacholl.formica.repo

import mateuszmacholl.formica.model.answer.Answer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface AnswerRepo : JpaRepository<Answer, Int>, JpaSpecificationExecutor<Answer> {
}