package mateuszmacholl.formica.config.security

import mateuszmacholl.formica.config.jwt.JWTAuthenticationFilter
import mateuszmacholl.formica.config.jwt.JWTAuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.session.HttpSessionEventPublisher

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile(value = ["development"])
class SecurityConfig @Autowired
constructor(@param:Qualifier("customUserDetailsService") private val userDetailsService: UserDetailsService,
            private val bCryptPasswordEncoder: BCryptPasswordEncoder) : WebSecurityConfigurerAdapter() {

    private val jwtAuthenticationFilter: JWTAuthenticationFilter
        @Throws(Exception::class)
        get() {
            val filter = JWTAuthenticationFilter(authenticationManager())
            filter.setFilterProcessesUrl("/auth/login")
            return filter
        }


    @Autowired
    @Throws(Exception::class)
    fun configureGlobalSecurity(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/users/login").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/enabled").permitAll()
                .antMatchers(HttpMethod.POST, "/users/verification-token").permitAll()
                .antMatchers(HttpMethod.POST, "/users/password-reset-token").permitAll()
                .antMatchers(HttpMethod.POST, "/users/password").permitAll()
                .anyRequest().authenticated()
                //.antMatchers("/**").permitAll()
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilter(JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**")
    }

    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
    }


    @Bean
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

    @Bean
    fun httpSessionEventPublisher(): HttpSessionEventPublisher {
        return HttpSessionEventPublisher()
    }
}
