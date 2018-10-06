package mateuszmacholl.formica.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mateuszmacholl.formica.model.user.User;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res) throws AuthenticationException {
		User user = getUserFromRequest(req);

		List<SimpleGrantedAuthority> authorities = getAuthoritiesFromUser(user);

		return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						user.getUsername(),
						user.getPassword(),
						authorities)
		);

	}

	private User getUserFromRequest(HttpServletRequest req){
		try {
			return  new ObjectMapper()
					.readValue(req.getInputStream(), User.class);
		} catch (IOException e) {
			throw new RuntimeException("attempt authentication failed");
		}
	}

	private List<SimpleGrantedAuthority> getAuthoritiesFromUser(User user){
		return user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.collect(Collectors.toList());
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req,
	                                        HttpServletResponse res,
	                                        FilterChain chain,
	                                        Authentication auth) {
		String token = generateToken(auth);
		res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
	}

	private String generateToken(Authentication auth){
		return Jwts.builder()
				.setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
				.claim("roles", auth.getAuthorities())
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.compact();
	}

	@Override
	public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
			JSONObject json = generateJson(HttpStatus.UNAUTHORIZED, "Account is not activated or credentials are wrong");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write(String.valueOf(json));
	}

	private JSONObject generateJson(HttpStatus httpStatus, String message) {
		JSONObject json = new JSONObject();
		try {
			json.put("timestamp", LocalDateTime.now());
			json.put("status", httpStatus.value());
			json.put("error", httpStatus.getReasonPhrase());
			json.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}