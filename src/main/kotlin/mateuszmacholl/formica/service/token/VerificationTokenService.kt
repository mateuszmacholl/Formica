package mateuszmacholl.formica.service.token

import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.model.user.VerificationToken
import mateuszmacholl.formica.repo.VerificationTokenRepo
import mateuszmacholl.formica.specification.VerificationTokenSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class VerificationTokenService {
    @Autowired
    lateinit var verificationTokenRepo: VerificationTokenRepo

    fun findByToken(token: String): VerificationToken? {
        return verificationTokenRepo.findByToken(token)
    }

    fun add(verificationToken: VerificationToken): VerificationToken{
        val id = verificationToken.user!!.id
        if(verificationTokenRepo.findById(id!!).isPresent){
            deleteById(id)
        }
        return verificationTokenRepo.save(verificationToken)
    }


    fun deleteByToken(token: String) {
        val tokenToDelete = findByToken(token)
        if (tokenToDelete != null) {
            verificationTokenRepo.delete(tokenToDelete)
        }
    }

    fun deleteById(id: Int){
        verificationTokenRepo.deleteById(id)
    }


    fun findAll(verificationTokenSpec: VerificationTokenSpec, pageable: Pageable) : MutableIterable<VerificationToken> {
        return verificationTokenRepo.findAll(verificationTokenSpec, pageable)
    }

    fun delete(verificationToken: VerificationToken){
        verificationTokenRepo.delete(verificationToken)
    }

    fun findById(id: Int): Optional<VerificationToken> {
        return verificationTokenRepo.findById(id)
    }

    fun generateToken(user: User): VerificationToken{
        val token = UUID.randomUUID().toString()
        val verificationToken = VerificationToken(token = token, user = user)
        return add(verificationToken)
    }


}