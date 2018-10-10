package mateuszmacholl.formica.service.user

import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.repo.UserRepo
import mateuszmacholl.formica.specification.UserSpec
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService
constructor(private val userRepo: UserRepo, private val passwordEncoderService: PasswordEncoderService) {

    fun findById(id: Int): Optional<User> {
        return userRepo.findById(id)
    }

    fun findAll(userSpec: UserSpec, pageable: Pageable): MutableIterable<User> {
        return userRepo.findAll(userSpec, pageable)
    }

    fun add(user: User) {
        encodeUserPassword(user)
        userRepo.save(user)
    }

    fun enableUser(user: User) {
        user.enabled = true
        add(user)
    }

    fun changePassword(user: User, newPassword: String) {
        user.password = newPassword
        encodeUserPassword(user)
        add(user)
    }

    fun encodeUserPassword(user: User) {
        user.password = passwordEncoderService.encodePassword(user.password!!)
    }

    fun delete(user: User) {
        userRepo.delete(user)
    }

    fun findByEmail(mail: String): User? {
        return userRepo.findByEmail(mail)
    }

    fun findByUsername(username: String): User? {
        return userRepo.findByUsername(username)
    }

}
