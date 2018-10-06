package mateuszmacholl.formica.service.user.token

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

    fun getToken(token: String): VerificationToken? {
        return verificationTokenRepo.findByToken(token)
    }

    fun add(verificationToken: VerificationToken){
        val id = verificationToken.user!!.id
        if(verificationTokenRepo.findById(id!!).isPresent){
            deleteById(id)
        }
        verificationTokenRepo.save(verificationToken)
    }


    fun deleteByToken(token: String) {
        val tokenToDelete = getToken(token)
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


}