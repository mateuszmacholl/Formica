package mateuszmacholl.formica.controller.user

import mateuszmacholl.formica.converter.token.VerificationTokenConverter
import mateuszmacholl.formica.dto.token.verificationToken.CreateVerificationTokenDto
import mateuszmacholl.formica.service.token.VerificationTokenService
import mateuszmacholl.formica.specification.VerificationTokenSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/verification-tokens"])
class VerificationTokenController {
    @Autowired
    lateinit var verificationTokenService: VerificationTokenService
    @Autowired
    lateinit var verificationTokenConverter: VerificationTokenConverter

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(verificationTokenSpec: VerificationTokenSpec, pageable: Pageable): ResponseEntity<*> {
        val verificationTokens = verificationTokenService.findAll(verificationTokenSpec, pageable)
        return ResponseEntity(verificationTokens, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val verificationToken = verificationTokenService.findById(id)
        return if (!verificationToken.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(verificationToken.get(), HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createVerificationTokenDto: CreateVerificationTokenDto): ResponseEntity<*> {
        val verificationToken = verificationTokenConverter.toEntity(createVerificationTokenDto)
        verificationTokenService.add(verificationToken)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val verificationToken = verificationTokenService.findById(id)
        return if (!verificationToken.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            verificationTokenService.delete(verificationToken.get())
            ResponseEntity<Any>(HttpStatus.NO_CONTENT )
        }
    }

    @RequestMapping(value = ["/{id}/users"], method = [RequestMethod.GET])
    fun getUser(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val verificationToken = verificationTokenService.findById(id)
        return if (!verificationToken.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val user = verificationToken.get().user
            ResponseEntity<Any>(user, HttpStatus.OK )
        }
    }

}