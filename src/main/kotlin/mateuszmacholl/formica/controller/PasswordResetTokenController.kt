package mateuszmacholl.formica.controller

import mateuszmacholl.formica.converter.token.PasswordResetTokenConverter
import mateuszmacholl.formica.dto.token.passwordResetToken.CreatePasswordResetTokenDto
import mateuszmacholl.formica.service.token.PasswordResetTokenService
import mateuszmacholl.formica.specification.PasswordResetTokenSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/password-reset-tokens"])
class PasswordResetTokenController {
    @Autowired
    lateinit var passwordResetTokenService: PasswordResetTokenService
    @Autowired
    lateinit var passwordResetTokenConverter: PasswordResetTokenConverter

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(passwordResetTokenSpec: PasswordResetTokenSpec, pageable: Pageable): ResponseEntity<*> {
        val passwordResetTokens = passwordResetTokenService.findAll(passwordResetTokenSpec, pageable)
        return ResponseEntity(passwordResetTokens, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val passwordResetToken = passwordResetTokenService.findById(id)
        return if (!passwordResetToken.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(passwordResetToken.get(), HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createPasswordResetTokenDto: CreatePasswordResetTokenDto): ResponseEntity<*> {
        val passwordResetToken = passwordResetTokenConverter.toEntity(createPasswordResetTokenDto)
        passwordResetTokenService.add(passwordResetToken)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val passwordResetToken = passwordResetTokenService.findById(id)
        return if (!passwordResetToken.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            passwordResetTokenService.delete(passwordResetToken.get())
            ResponseEntity<Any>(HttpStatus.NO_CONTENT )
        }
    }

    @RequestMapping(value = ["/{id}/users"], method = [RequestMethod.GET])
    fun getUser(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val passwordResetToken = passwordResetTokenService.findById(id)
        return if (!passwordResetToken.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val user = passwordResetToken.get().user
            ResponseEntity<Any>(user, HttpStatus.OK )
        }
    }



}