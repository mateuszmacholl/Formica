package mateuszmacholl.formica.config.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import mateuszmacholl.formica.model.user.User
import org.springframework.boot.configurationprocessor.json.JSONException
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.time.LocalDateTime
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.streams.toList


class JWTAuthenticationFilter(authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

    init {
        this.authenticationManager = authenticationManager
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(req: HttpServletRequest,
                                       res: HttpServletResponse?): Authentication {
        val user = getUserFromRequest(req)

        val authorities = getAuthoritiesFromUser(user)

        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        user.username,
                        user.password,
                        authorities)
        )

    }

    private fun getUserFromRequest(req: HttpServletRequest): User {
        try {
            return ObjectMapper()
                    .readValue(req.inputStream, User::class.java)
        } catch (e: IOException) {
            throw RuntimeException("attempt authentication failed")
        }

    }

    private fun getAuthoritiesFromUser(user: User): List<SimpleGrantedAuthority> {
        return user.roles.stream()
                .map { role -> SimpleGrantedAuthority("ROLE_$role") }.toList()
    }

    override fun successfulAuthentication(req: HttpServletRequest,
                                          res: HttpServletResponse,
                                          chain: FilterChain?,
                                          auth: Authentication) {
        val token = generateToken(auth)
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token)
    }

    private fun generateToken(auth: Authentication): String {
        return Jwts.builder()
                .setSubject((auth.principal as org.springframework.security.core.userdetails.User).username)
                .claim("roles", auth.authorities)
                .setExpiration(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact()
    }

    @Throws(IOException::class)
    public override fun unsuccessfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, failed: AuthenticationException) {
        val json = generateJson(HttpStatus.UNAUTHORIZED, "Account is not activated or credentials are wrong")
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.writer.write(json.toString())
    }

    private fun generateJson(httpStatus: HttpStatus, message: String): JSONObject {
        val json = JSONObject()
        try {
            json.put("timestamp", LocalDateTime.now())
            json.put("status", httpStatus.value())
            json.put("error", httpStatus.reasonPhrase)
            json.put("message", message)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return json
    }
}