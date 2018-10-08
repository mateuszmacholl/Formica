package mateuszmacholl.formica.service.jwt

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

object JwtData {
    fun getUsername(): String {
        return SecurityContextHolder.getContext().authentication.principal as String
    }

    fun hasRole(role: String): Boolean{
        return SecurityContextHolder.getContext().authentication.authorities.contains(SimpleGrantedAuthority("[$role]"))
    }
}