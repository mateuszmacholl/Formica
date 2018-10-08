package mateuszmacholl.formica.service.user


import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class CustomUserDetailsService @Autowired
constructor(private val userRepo: UserRepo) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        val user = userRepo.findByUsername(username) ?: throw UsernameNotFoundException(
                "No user found with username: $username")
        return org.springframework.security.core.userdetails.User(user.username,
                user.password, user.enabled, true,
                true, true,
                getGrantedAuthorities(user))
    }

    private fun getGrantedAuthorities(user: User): List<GrantedAuthority> {
        val authorities = ArrayList<GrantedAuthority>()

        for (role in user.roles) {
            println("role : $role")
            authorities.add(SimpleGrantedAuthority("role_$role"))
        }
        print("authorities :$authorities")
        return authorities
    }
}

