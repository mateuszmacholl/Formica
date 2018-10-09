package mateuszmacholl.formica.repo

import mateuszmacholl.formica.model.token.PasswordResetToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PasswordResetTokenRepo : JpaRepository<PasswordResetToken, Int>, JpaSpecificationExecutor<PasswordResetToken> {
    fun findByToken(token: String): PasswordResetToken?
}