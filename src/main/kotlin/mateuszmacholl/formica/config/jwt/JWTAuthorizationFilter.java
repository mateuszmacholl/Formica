package mateuszmacholl.formica.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        if(isHeaderIncorrect(req.getHeader(SecurityConstants.HEADER_STRING))){
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private boolean isHeaderIncorrect(String header){
        if(header != null){
            return !header.startsWith(SecurityConstants.TOKEN_PREFIX);
        }
	    return true;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (isTokenValid(token)) {
            Claims claims = parseJwt(token);
            String username = claims.getSubject();
            if (usernameExists(username)) {
                ArrayList<SimpleGrantedAuthority> authorities = parseRolesClaimsToAuthorities(claims);
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
            return null;
        }
        return null;
    }

    private boolean isTokenValid(String token){
        return token != null;
    }

    private Claims parseJwt(String token){
        return Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getBody();
    }

    private boolean usernameExists(String username){
        return username != null;
    }

    private ArrayList<SimpleGrantedAuthority> parseRolesClaimsToAuthorities(Claims claims){
	    ArrayList<LinkedHashMap> claimsRolesMap = (ArrayList<LinkedHashMap>) claims.get("roles");
	    List<Collection> claimsRolesList = claimsRolesMap.stream().map((Function<LinkedHashMap, Collection>) LinkedHashMap::values).collect(Collectors.toList());

	    ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
	    claimsRolesList.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.toString())));
	    return authorities;
    }
}