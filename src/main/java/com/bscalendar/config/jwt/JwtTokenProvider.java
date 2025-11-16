package com.bscalendar.config.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.bscalendar.member.dto.MemberDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final Key key;
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 20;
	private static final long RE_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;
	
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	
	// JWT 토큰 생성
	// 유효기간 생성일자로부터 30분
	public String createToken(Authentication authentication) {
		String username = authentication.getName();
		String memberName = null;
		
		Object principal = authentication.getPrincipal();
		if(principal instanceof MemberDetails) {
			memberName = ((MemberDetails) principal).getMemberName();
		}
		
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);
		
		return Jwts.builder()
				   .setSubject(username)
				   .claim("name", memberName)
				   .setIssuedAt(now)
				   .setExpiration(expireDate)
				   .signWith(key, SignatureAlgorithm.HS256)
				   .compact();
	}
	
	// JWT Refresh토큰 생성
	// 유효기간 생성일자로부터 7일
	public String createRefreshToken(Authentication authentication) {
		String username = authentication.getName();
		String memberName = null;
		
		Object principal = authentication.getPrincipal();
		if(principal instanceof MemberDetails) {
			memberName = ((MemberDetails) principal).getMemberName();
		}
		
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + RE_TOKEN_EXPIRE_TIME);
		
		return Jwts.builder()
				   .setSubject(username)
				   .claim("name", memberName)
				   .claim("type", "refresh")
				   .setIssuedAt(now)
				   .setExpiration(expireDate)
				   .signWith(key, SignatureAlgorithm.HS256)
				   .compact();
	}
	
	// JWT 토큰에서 사용자명 추출
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parserBuilder()
				            .setSigningKey(key)
				            .build()
				            .parseClaimsJws(token)
				            .getBody();
		
		return claims.getSubject();
	}
	
	// JWT 토큰 유효성 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			    .setSigningKey(key)
			    .build()
			    .parseClaimsJws(token);
			return true;
		} catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			// 잘못된 JWT 서명
			e.printStackTrace();
		} catch(ExpiredJwtException e) {
			// 만료된 JWT 토큰
			System.out.println("만료된 JWT 토큰을 포함한 요청이 들어왔습니다.");
		} catch(UnsupportedJwtException e) {
			// 지원하지 않는 JWT 토큰
			e.printStackTrace();
		} catch(IllegalArgumentException e) {
			// 잘못된 JWT 토큰
			e.printStackTrace();
		}
		return false;
	}
}
