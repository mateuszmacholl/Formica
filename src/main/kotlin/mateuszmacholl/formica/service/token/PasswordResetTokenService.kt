package mateuszmacholl.formica.service.token

import mateuszmacholl.formica.model.token.PasswordResetToken
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.repo.PasswordResetTokenRepo
import mateuszmacholl.formica.specification.PasswordResetTokenSpec
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PasswordResetTokenService(private val passwordResetTokenRepo: PasswordResetTokenRepo) {
    fun findByToken(token: String): PasswordResetToken? {
        return passwordResetTokenRepo.findByToken(token)
    }

    fun add(passwordResetToken: PasswordResetToken): PasswordResetToken {
        val id = passwordResetToken.user!!.id
        if (passwordResetTokenRepo.findById(id!!).isPresent) {
            deleteById(id)
        }
        return passwordResetTokenRepo.save(passwordResetToken)
    }


    fun deleteByToken(token: String) {
        val tokenToDelete = findByToken(token)
        if (tokenToDelete != null) {
            passwordResetTokenRepo.delete(tokenToDelete)
        }
    }

    fun deleteById(id: Int) {
        passwordResetTokenRepo.deleteById(id)
    }

    fun findAll(passwordResetTokenSpec: PasswordResetTokenSpec, pageable: Pageable): MutableIterable<PasswordResetToken> {
        return passwordResetTokenRepo.findAll(passwordResetTokenSpec, pageable)
    }

    fun delete(passwordResetToken: PasswordResetToken) {
        passwordResetTokenRepo.delete(passwordResetToken)
    }

    fun findById(id: Int): Optional<PasswordResetToken> {
        return passwordResetTokenRepo.findById(id)
    }

    fun generateToken(user: User): PasswordResetToken {
        val token = UUID.randomUUID().toString()
        val passwordResetToken = PasswordResetToken(token = token, user = user)
        return add(passwordResetToken)
    }

}