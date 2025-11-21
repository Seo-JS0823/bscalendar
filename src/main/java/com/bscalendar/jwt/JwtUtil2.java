package com.bscalendar.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil2 {

	private static Values values;
    
	public JwtUtil2(@Value("${jwt.secret}") String secretKey, @Value("${jwt.issuer}") String issuer, @Value("${jwt.expiration}") long expiration, @Value("${jwt.refresh}") long refresh)
	{values = new Values(secretKey, issuer, expiration, refresh);}
	
	private SecretKey generalKey(){return Keys.hmacShaKeyFor(values.secretKey.getBytes());}
	public boolean isExpired(String token, String x) {return Jwts.parser().verifyWith(generalKey()).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());} // End of isExpired()

	/** Create {@code JWT Token} with json object
	 * @return Stringified Token */
	public String createJwt(JSONObject json){
		String result = null, value = null;
		JwtBuilder builder = null;

		builder = Jwts.builder();
		for(String key: json.keySet()){
			value = json.get(key).toString();
			builder.claim(key, value);
		}
		
		result = builder.issuedAt(new Date(System.currentTimeMillis()))
						.expiration(new Date(System.currentTimeMillis() + values.expiration))
						.signWith(generalKey())
						.compact();
		return result;
	} // End of createJwt()

	/** Create {@code JWT Token} with json object
	 * @return Stringified Token */
	public String createJwt(HashMap<String, String> map){
		String result = null, value = null;
		JwtBuilder builder = null;

		builder = Jwts.builder();
		for(String key : map.keySet()){
			value = map.get(key);
			builder.claim(key, value);
		}
		
		result = builder.issuedAt(new Date(System.currentTimeMillis()))
						.expiration(new Date(System.currentTimeMillis() + values.expiration*1000))
						.signWith(generalKey())
						.compact();
		return result;
	} // End of createJwt()
	
	/** get value from {@code JWT Token} with key
	 * @return value in payload */
	public String getValue(String token, String key) {return (Jwts.parser().verifyWith(generalKey()).build().parseSignedClaims(token).getPayload().containsKey(key)) ? (String)Jwts.parser().verifyWith(generalKey()).build().parseSignedClaims(token).getPayload().get(key) : null;} // End of getValue()

	/** get value from {@code JWT Token} with key
	 * @return value in payload */
	public String[] getValues(String token, String... keys) {
			if(token == null || keys == null)   return null;
			String[] result = new String[keys.length];
			boolean exist = false;
			for(int x = 0 ; x < keys.length ; x++){
				exist = Jwts.parser().verifyWith(generalKey()).build().parseSignedClaims(token).getPayload().containsKey(keys[x]);
				result[x] = exist ? (String)Jwts.parser().verifyWith(generalKey()).build().parseSignedClaims(token).getPayload().get(keys[x]) : null;
			}
			return result;
	} // End of getValue()

	public Authentication getAuthentication(String token){
		Collection<? extends GrantedAuthority> authorities = null;
		SecurityUser user = null;
		Claims claims = null;
		String auth = null;

		claims = parseClaims(token);
		auth = claims.get("auth").toString();
		authorities = Arrays.stream(auth.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		user = new SecurityUser();
		user.setUserId(claims.get("userId").toString());
		user.setName(claims.get("userName").toString());

		return new UsernamePasswordAuthenticationToken(claims, authorities);
	} // End of getAuthentication()

	public Boolean isExpired(String token) {
		boolean b = true;
		try{b = parseClaims(token).getExpiration().before(new Date());}catch(Exception e){}
		return b;
	}

	// accessToken을 파싱하여 Claims 객체를 반환
	private Claims parseClaims(String token) {
		try{return Jwts.parser().verifyWith(generalKey()).build().parseSignedClaims(token).getPayload();}
		catch(ExpiredJwtException e){log.error("Token is expired.");return null;}
		catch(MalformedJwtException e){log.error("Token is Malformed.");return null;}
	} // End of parseClaims()

} // End of class === JwtUtil


class Values{
	public String secretKey;
	public String issuer;
	public long expiration;
	public long refresh;
	
	public Values(String secretKey, String issuer, long expiration, long refresh){
		this.secretKey = secretKey;
		this.issuer = issuer;
		this.expiration = expiration;
		this.refresh = refresh;
	}
}