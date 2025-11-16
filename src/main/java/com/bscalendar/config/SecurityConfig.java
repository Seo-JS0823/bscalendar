package com.bscalendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bscalendar.config.jwt.JwtAuthenticationFilter;
import com.bscalendar.config.jwt.JwtTokenProvider;
import com.bscalendar.member.service.MemberDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JwtTokenProvider tokenProvider;
	private final MemberDetailsService detailsService;
	
	public SecurityConfig(
			JwtTokenProvider tokenProvider,
			MemberDetailsService detailsService) {
		this.tokenProvider = tokenProvider;
		this.detailsService = detailsService;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				// 인증 불필요 경로
				.requestMatchers(	
					// 로그인 페이지
					"/",
					// 비번 찾기 페이지
					"/find",
					// 회원가입 페이지
					"/join",
					// 마이페이지
					"/mypage",
					// Login 요청 -> JWT Token 응답
					"/api/member/login",
					// 프로젝트 페이지들
					"/project/**",
					// 업무 페이지들
					"/work/**",
					// JSP Form Load 경로s
					"/WEB-INF/views/**",
					// 정적 리소스들,
					"/css/**", "/js/**", "/img/**",
					// 토큰 재생성,
					"/api/member/auth/re",
					// 에러
					"/error"
				).permitAll()
				// 인증 필요 경로
				.requestMatchers(
					// 마이페이지 데이터 로드
					"/api/member/mypage/**",
					// 모든 프로젝트 기능
					"/api/project/**",
					// 모든 업무 기능
					"/api/work/**",
					// 모든 메모 기능
					"/api/reply/**"
				).hasRole("BSC")
				.anyRequest().authenticated())
			.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.formLogin(form -> form.disable())
			.httpBasic(basic -> basic.disable());
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(tokenProvider, detailsService);
	}
	
}
