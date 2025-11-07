package com.bscalendar.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;

public class JWTUtil {

	private SecretKey secretKey;
	
	public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
		// value 값을 가져와서 Jwts.SIG.HS256.key() -> 객체 키(SecretKey)를 생성
		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
	}
	
	// 파싱된 토큰의 페이로드에서 username을 가져옴
	public String getUsername(String token) {
		return Jwts.parser().verifyWith(secretKey).build()
				.parseSignedClaims(token).getPayload().get("username", String.class);
	}
	
	// 파싱된 토큰의 페이로드에서 role을 가져옴
	public String getRole(String token) {
		return Jwts.parser().verifyWith(secretKey).build()
				.parseSignedClaims(token).getPayload().get("role", String.class);
	}
	
	// 토큰 만료되었는지 확인
	public Boolean isExpired(String token) {
		return Jwts.parser().verifyWith(secretKey).build()
				.parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}
	
	// 토큰 생성
	public String createJwt(String username, String role, Long expiredMs) {
		
		return Jwts.builder()
				.claim("username", username)
				.claim("role", role)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiredMs))
				.signWith(secretKey)
				.compact();
	}
}