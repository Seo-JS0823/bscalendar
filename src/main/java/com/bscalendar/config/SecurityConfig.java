package com.bscalendar.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.bscalendar.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired private JwtFilter jwtFilter;
	private AuthenticationConfiguration authenticationConfiguration;

	public SecurityConfig(AuthenticationConfiguration a){authenticationConfiguration = a;}

	@Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration c) throws Exception{return c.getAuthenticationManager();}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(a -> a.disable()) // CORS 정책 꺼둠
		.formLogin(a -> a.disable()) // 스프링 로그인 폼 꺼두기
		.httpBasic(a -> a.disable()) // 스프링 시큐리티의 기본 http 기능 꺼둠
		.sessionManagement(a -> a.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 기능도 꺼둠 :: 꺼지기는 할까? ㅎㅎㅎ
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // JWT를 검증하거나 말거나 하는 필터를 등록해 둠
		.authorizeHttpRequests(a -> a // 무조건 허용할 요청의 주소와 검증 수행할 주소 등록
				.requestMatchers("/**").permitAll()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // preflight 도 무조건 허용 // 브라우저에서 선처리 하는 호구조사 같은 것이며 option 메서드 요청을 은밀하게 수행함
				.anyRequest().authenticated()); // 그 외 무조건 검증
		
		return http.build();
		
		//.requestMatchers("/**").permitAll()
		// .requestMatchers("/css/**", "/js/**", "/img/**", "/joinForm", "/join", "/loginForm", "/login", "/", "/index", "favicon.ico").permitAll() // 이놈들은 무조건 허용
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration cors = new CorsConfiguration();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		cors.addAllowedOrigin("*");
		cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		cors.addAllowedHeader("*");
		cors.setAllowCredentials(true);
		cors.setMaxAge(3600L);
		cors.addExposedHeader("Authorization");
		source.registerCorsConfiguration("/**", cors);
		
		return source;
	} // End of corsConfigurationSource()
}