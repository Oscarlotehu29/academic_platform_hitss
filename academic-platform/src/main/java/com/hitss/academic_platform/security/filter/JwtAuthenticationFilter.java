package com.hitss.academic_platform.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.hitss.academic_platform.security.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.hitss.academic_platform.security.TokenJwtConfig.PREFIX_TOKEN;
import static com.hitss.academic_platform.security.TokenJwtConfig.SECRET_KEY;
import static com.hitss.academic_platform.security.TokenJwtConfig.CONTENT_TYPE;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitss.academic_platform.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		User user = null;
		String userName = null;
		String passwd = null;
		
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), User.class);//Convertimos el json que recibimos a un objeto del tipo usuario de nuestras entidades.
			
			userName = user.getUserName();
			passwd = user.getPassword();
		} 
		catch(StreamReadException e) {
			e.printStackTrace();
		}
		catch(DatabindException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		UsernamePasswordAuthenticationToken authenticationToken = new 
				UsernamePasswordAuthenticationToken(userName, passwd);
		
		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		org.springframework.security.core.userdetails.User user = 
				(org.springframework.security.core.userdetails.User) authResult.getPrincipal();
		
		Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
		
		String username = user.getUsername();
		
		
		Claims claimbs = Jwts.claims().
				add("authorities", new ObjectMapper().writeValueAsString(roles))
				.add("username", username)
				.build();
		
		String token = Jwts.builder()
				.subject(username)
				.claims(claimbs)
				.expiration(new Date(System.currentTimeMillis() + 3600000)) //milisegundos
				.issuedAt(new Date()) //Hora en que se genera
				.signWith(SECRET_KEY).compact(); 
		
		response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);//agregamos a la respuesta en la cabecera
		
		Map<String, String> body = new HashMap<>();
		body.put("token", token);
		body.put("username", username);
		body.put("message", String.format("Hola %s has iniciado sesión con exito!!!", username));
		
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));//Convertimos al mapa a un Json si tiene la estructura necesaria para convertirse en json
		response.setContentType(CONTENT_TYPE);
		
		response.setStatus(200);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		Map<String, String> body = new HashMap<>();
		
		body.put("message", "Error en la autenticación, username o password incorrecto !!");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		
		response.setStatus(401);
		response.setContentType(CONTENT_TYPE);
	}
	
}
