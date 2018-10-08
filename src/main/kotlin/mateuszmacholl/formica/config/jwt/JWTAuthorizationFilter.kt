package mateuszmacholl.formica.config.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.streams.toList

class JWTAuthorizationFilter(authManager: AuthenticationManager) : BasicAuthenticationFilter(authManager) {
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(req: HttpServletRequest,
                                  res: HttpServletResponse,
                                  chain: FilterChain) {

        if (isHeaderIncorrect(req.getHeader(SecurityConstants.HEADER_STRING))) {
            chain.doFilter(req, res)
            return
        }

        val authentication = getAuthentication(req)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(req, res)
    }

    private fun isHeaderIncorrect(header: String?): Boolean {
        return if (header != null) {
            !header.startsWith(SecurityConstants.TOKEN_PREFIX)
        } else true
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(SecurityConstants.HEADER_STRING)
        if (isTokenValid(token)) {
            val claims = parseJwt(token)
            val username = claims.subject
            if (usernameExists(username)) {
                val authorities = parseRolesClaimsToAuthorities(claims)
                return UsernamePasswordAuthenticationToken(username, null, authorities)
            }
            return null
        }
        return null
    }

    private fun isTokenValid(token: String?): Boolean {
        return token != null
    }

    private fun usernameExists(username: String?): Boolean {
        return username != null
    }

    private fun parseJwt(token: String): Claims {
        return Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .body
    }

    private fun parseRolesClaimsToAuthorities(claims: Claims): ArrayList<SimpleGrantedAuthority> {
        val claimsRolesMap = claims["roles"] as ArrayList<LinkedHashMap<*, *>>
        val claimsRolesList = claimsRolesMap.stream().map { it.values }.toList()

        val authorities = ArrayList<SimpleGrantedAuthority>()
        claimsRolesList.forEach { role -> authorities.add(SimpleGrantedAuthority(role.toString())) }
        return authorities
    }
}