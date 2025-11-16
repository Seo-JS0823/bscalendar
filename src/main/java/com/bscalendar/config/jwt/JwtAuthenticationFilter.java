package com.bscalendar.config.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bscalendar.member.service.MemberDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider tokenProvider;
	private final MemberDetailsService memberDetailsService;
	
	public JwtAuthenticationFilter(
			JwtTokenProvider tokenProvider,
			MemberDetailsService memberDetailsService) {
		this.tokenProvider = tokenProvider;
		this.memberDetailsService = memberDetailsService;
	}
	
	// Authorization 헤더에서 JWT 토큰 추출
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		String jwt = getJwtFromRequest(request);
		String path = request.getServletPath();
		
		/*
		if(path.equals("/")
		   || path.startsWith("/api/member/auth")
		   || path.startsWith("/api/member/login")
		   || path.startsWith("/join")
		   || path.startsWith("/find")) {
			System.out.println("JWT 미인증 PATH : " + path);
			filterChain.doFilter(request, response);
			return;
		} else {
			System.out.println("JWT 인증하는 PATH : " + path);
		}
		*/
		if(jwt != null && tokenProvider.validateToken(jwt)) {
			String username = tokenProvider.getUsernameFromToken(jwt);
			UserDetails details = memberDetailsService.loadUserByUsername(username);
			
			System.out.println("TOKEN : " + jwt);
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				details, null, details.getAuthorities()
			);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			System.out.println("인증된 PATH : " + path);
		}
		filterChain.doFilter(request, response);
	}
	
}
