package mateuszmacholl.formica.service.user.token

import mateuszmacholl.formica.model.user.PasswordResetToken
import mateuszmacholl.formica.repo.PasswordResetTokenRepo
import mateuszmacholl.formica.specification.PasswordResetTokenSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PasswordResetTokenService {
    @Autowired
    lateinit var passwordResetPasswordResetTokenRepo: PasswordResetTokenRepo

    fun findByToken(token: String): PasswordResetToken? {
        return passwordResetPasswordResetTokenRepo.findByToken(token)
    }

    fun add(passwordResetToken: PasswordResetToken){
        val id = passwordResetToken.user!!.id
        if(passwordResetPasswordResetTokenRepo.findById(id!!).isPresent){
            deleteById(id)
        }
        passwordResetPasswordResetTokenRepo.save(passwordResetToken)
    }


    fun deleteByToken(token: String) {
        val tokenToDelete = findByToken(token)
        if (tokenToDelete != null) {
            passwordResetPasswordResetTokenRepo.delete(tokenToDelete)
        }
    }

    fun deleteById(id: Int){
        passwordResetPasswordResetTokenRepo.deleteById(id)
    }

    fun findAll(passwordResetTokenSpec: PasswordResetTokenSpec, pageable: Pageable) : MutableIterable<PasswordResetToken> {
        return passwordResetPasswordResetTokenRepo.findAll(passwordResetTokenSpec, pageable)
    }

    fun delete(passwordResetToken: PasswordResetToken){
        passwordResetPasswordResetTokenRepo.delete(passwordResetToken)
    }

    fun findById(id: Int): Optional<PasswordResetToken> {
        return passwordResetPasswordResetTokenRepo.findById(id)
    }

}