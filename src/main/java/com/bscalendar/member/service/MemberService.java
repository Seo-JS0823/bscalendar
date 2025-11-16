package com.bscalendar.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bscalendar.config.jwt.JwtTokenProvider;
import com.bscalendar.member.dto.response.TokenResponseDTO;
import com.bscalendar.member.mapper.MemberMapper;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	public String getRefreshToken(String mem_id) {
		return memberMapper.findRefreshToken(mem_id);
	}
	
	// true  : 업데이트 되었으면 ture
	// false : 업데이트 안되었으면 false
	public boolean refreshTokenUpdate(String mem_id, String refresh_token) {
		int updated = memberMapper.refreshTokenUpdate(mem_id, refresh_token);
		return updated > 0;
	}
	
	public Map<String, Object> refreshToken(
			String reToken, 
			JwtTokenProvider tokenProvider,
			MemberDetailsService memberDetailsService) {
		
		Map<String, Object> response = null;
		if(!tokenProvider.validateToken(reToken)) {
			response = Map.of(
				"message", "로그인을 재시도 하시기 바랍니다."
			);
			return response;
		}
		
		String memberId = tokenProvider.getUsernameFromToken(reToken);
		String memberRefreshToken = getRefreshToken(memberId);
		
		// TODO: RefreshToken 일치 여부
		if(!reToken.equals(memberRefreshToken)) {
			System.out.println("Refresh Token이 일치하지 않습니다.");
			response = Map.of(
				"message", "Refresh Token Miss Match of Unauthorized"
			);
			return response;
		}
		
		// TODO: 새 ACCESS TOKEN 생성
		try {
			UserDetails memberDetails = memberDetailsService.loadUserByUsername(memberId);
			
			Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
				memberDetails, null, memberDetails.getAuthorities()
			);
			
			String newAccessToken = tokenProvider.createToken(newAuthentication);
			String newRefreshToken = tokenProvider.createRefreshToken(newAuthentication);
			
			// TODO: 새 리프레시 토큰 DB 저장
			refreshTokenUpdate(memberId, newRefreshToken);
			
			response = Map.of(
				"token", new TokenResponseDTO(newAccessToken).getToken(),
				"refresh_token", new TokenResponseDTO(newRefreshToken).getToken()
			);
			return response;
		} catch(Exception e) {
			response = Map.of(
				"message", "사용자 정보 로드 중 오류가 발생하였습니다."
			);
			return response;
		}
	}
}
