package com.hitss.academic_platform.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import static com.hitss.academic_platform.security.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.hitss.academic_platform.security.TokenJwtConfig.PREFIX_TOKEN;
import static com.hitss.academic_platform.security.TokenJwtConfig.SECRET_KEY;
import static com.hitss.academic_platform.security.TokenJwtConfig.CONTENT_TYPE;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitss.academic_platform.security.SimpleGrantedAuthorityJsonCreator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValitationFilter extends BasicAuthenticationFilter{
	public JwtValitationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(HEADER_AUTHORIZATION);
		
		if(header == null || !header.startsWith(PREFIX_TOKEN)) {
			chain.doFilter(request, response);
			return;
		}
		
	String token = header.replace(PREFIX_TOKEN, "");
	
	try {
		
		Claims claims = Jwts.parser().verifyWith(SECRET_KEY)
				.build().parseSignedClaims(token).getPayload();
		
		String username = claims.getSubject();
		Object authoritiesClaims = claims.get("authorities");
		
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(
				new ObjectMapper()
				.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
				.readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
		
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		
		chain.doFilter(request, response);
		System.out.println("Authorities: " + authorities);
		System.out.println("Method: " + request.getMethod() + " URI: " + request.getRequestURI());


		
		//Roles se convierten en St
		
	} catch (JwtException e) {
		Map<String, String> body = new HashMap<>();
		
		body.put("error", e.getMessage());
		body.put("message", "El token JWT no es valido");
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		response.setContentType(CONTENT_TYPE);
	}
	
	}
}
