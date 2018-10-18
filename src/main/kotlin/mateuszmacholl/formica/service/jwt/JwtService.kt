package mateuszmacholl.formica.service.jwt

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service("JwtService")
class JwtService {
    fun getUsername(): String {
        return SecurityContextHolder.getContext().authentication.principal as String
    }

    fun hasRole(role: String): Boolean{
        return SecurityContextHolder.getContext().authentication.authorities.contains(SimpleGrantedAuthority("[$role]"))
    }
}