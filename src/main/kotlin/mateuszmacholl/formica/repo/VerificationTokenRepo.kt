package mateuszmacholl.formica.repo

import mateuszmacholl.formica.model.user.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface VerificationTokenRepo : JpaRepository<VerificationToken, Int>, JpaSpecificationExecutor<VerificationToken> {
    fun findByToken(token: String): VerificationToken?
}