package mateuszmacholl.formica.service.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("JwtRolesService")
public class JwtRolesService {
	public static Boolean hasRole(String role) {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("[" + role + "]"));
	}
}
