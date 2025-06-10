package com.hitss.academic_platform.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hitss.academic_platform.security.filter.JwtAuthenticationFilter;
import com.hitss.academic_platform.security.filter.JwtValitationFilter;

//import com.hists.app_crud_api.security.filter.JwtAuthenticationFilter;
//import com.hists.app_crud_api.security.filter.JwtValitationFilter;

@Configuration
public class SpringSecurityConfig {
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	PasswordEncoder passwordEncoder() { //Retorna el metodo de hasheo
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	 AuthenticationManager authenticationManager() throws Exception {
		 return authenticationConfiguration.getAuthenticationManager();
	 }


	//Crearemos un filtrado para indicar a que rutas se puede acceder
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(
				auth -> auth
//				.requestMatchers(HttpMethod.GET,"/api/users/listUsers").permitAll()
//				.requestMatchers(HttpMethod.POST,"/api/users/register").hasRole("ADMIN")
				.requestMatchers("/api/users/**").hasRole("ADMIN")
				.requestMatchers("/api/teachers/**").hasAnyRole("ADMIN", "TEACHER")
				.requestMatchers("/api/students/**").hasAnyRole("ADMIN", "STUDENT")
				.requestMatchers(HttpMethod.GET,"/api/subjects/listSubjects").hasRole("TEACHER")
				.requestMatchers("/api/subjects/**").hasRole("ADMIN")
				.requestMatchers("/api/courses/**").hasAnyRole("ADMIN")
				.requestMatchers("/api/schoolPeriods/**").hasRole("ADMIN")
				.requestMatchers("/api/grades/**").hasAnyRole("ADMIN", "STUDENT", "TEACHER")
				.requestMatchers("/api/subjects-materials/**").hasAnyRole("ADMIN", "TEACHER")
				.requestMatchers("/api/reports/**").hasRole("ADMIN")
				.requestMatchers("/api/roles/**").hasRole("ADMIN")
				.requestMatchers(
					    "/swagger-ui/**",
					    "/v3/api-docs/**",
					    "/swagger-resources/**",
					    "/swagger-ui.html",
					    "/webjars/**"
					).permitAll()
				.anyRequest()
				.authenticated())
				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
				.addFilter(new JwtValitationFilter(authenticationManager()))
				.csrf(c -> c.disable())
				.sessionManagement(
						management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))	
				.build();
				
	}
	

}
