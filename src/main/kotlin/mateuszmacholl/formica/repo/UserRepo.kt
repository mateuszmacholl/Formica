package mateuszmacholl.formica.repo

import mateuszmacholl.formica.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface UserRepo : JpaRepository<User, Int>, JpaSpecificationExecutor<User> {
    fun findByUsername(@Param("username") username: String): User?
    fun findByEmail(@Param("email") email: String): User?
}