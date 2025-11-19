package com.bscalendar.jwt;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter{

  @Autowired private JwtUtil jwtUtil;
  @Value("${jwt.secret}") private String secretKey;
  private Logger accessLogger = LoggerFactory.getLogger("accessLogger");

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String auth = null, token = null, id = null, pw = null;
		Authentication authToken = null;
		SecurityUser user = null;

		response.addHeader("Access-Control-Allow-Origin", "*");
		accessLogger.info("{} / {}", request.getRemoteAddr(), request.getRequestURI());
		if(request.getMethod().equals("OPTIONS")){ // preflight 요청은 통과시킴
			response.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
			response.addHeader("Access-Control-Max-Age", "43200");
			return;
		}

		auth = request.getHeader("Authorization");

		// ====================================== 필터 적용 후 다시 실행시켜야 함 ===== 토큰 있는지 검사 ================================
		if (auth == null || !auth.startsWith("Bearer ")) { filterChain.doFilter(request, response); return; }

		// ====================================== 필터 적용 후 다시 실행시켜야 함 ===== 토큰 형식 검사 ================================
		token = auth.split(" ")[1]; // Bearer 부분 제거 후 순수 토큰만 획득
		log.info(token);
		
		// ====================================== 필터 적용 후 다시 실행시켜야 함 ===== 토큰 유효시간 검사 ================================
		 if(jwtUtil.isExpired(token)) { // 토큰 소멸 시간 검증
		 	filterChain.doFilter(request, response);
		 	return;
		 }

		// ====================================== 필터 적용 후 다시 실행시켜야 함 ===== 토큰 시큐리티 토큰을 위한 스프링 시큐리티 유조 생성 ================================
		user = new SecurityUser();
		user.setUserId("id");
		user.setName("name");
		user.setRole("USER");

		//스프링 시큐리티 인증 토큰 생성
		authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		accessLogger.info("authToken" + authToken);
		
		filterChain.doFilter(request, response);
  } // End of doFilterInternal()
  
} // End of class === JwtFilter