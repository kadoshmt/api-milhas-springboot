package br.com.janesroberto.milhas.security;



import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.*;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${milhas.app.jwtSecret}")
	private String jwtSecret;

	@Value("${milhas.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {
		System.out.println("Chamou JwtUtils.generateJwtToken() **************************************************************************");
		UserDetailsApi userPrincipal = (UserDetailsApi) authentication.getPrincipal();

		return Jwts.builder()
				//.setSubject((userPrincipal.getId().toString()))
				.setSubject((userPrincipal.getEmail()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS256, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		System.out.println("Chamou JwtUtils.getUserNameFromJwtToken() **************************************************************************");		
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		System.out.println("Chamou JwtUtils.validateJwtToken() **************************************************************************");
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
