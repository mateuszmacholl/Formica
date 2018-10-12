package mateuszmacholl.formica.service.answer

import mateuszmacholl.formica.model.answer.Answer
import mateuszmacholl.formica.repo.AnswerRepo
import mateuszmacholl.formica.specification.AnswerSpec
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class AnswerService
constructor(private val answerRepo: AnswerRepo) {
    fun findById(id: Int): Optional<Answer> {
        return answerRepo.findById(id)
    }

    fun findAll(answerSpec: AnswerSpec, pageable: Pageable): MutableIterable<Answer> {
        return answerRepo.findAll(answerSpec, pageable)
    }

    fun add(comment: Answer) {
        answerRepo.save(comment)
    }

    fun delete(comment: Answer) {
        answerRepo.delete(comment)
    }
}