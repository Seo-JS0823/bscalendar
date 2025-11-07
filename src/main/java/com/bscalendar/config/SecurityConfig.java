package com.bscalendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.bscalendar.jwt.JWTUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/**").permitAll()
				.anyRequest().authenticated())
			.formLogin(form -> form.disable());
			
		return http.build();
	}
	
	
	
	
}
