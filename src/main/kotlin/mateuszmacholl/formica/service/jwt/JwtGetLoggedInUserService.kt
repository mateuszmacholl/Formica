package mateuszmacholl.formica.service.jwt

import org.springframework.security.core.context.SecurityContextHolder

class JwtGetLoggedInUserService {
    companion object {
        fun getUsername(): String {
            return SecurityContextHolder.getContext().authentication.principal as String
        }
    }
}