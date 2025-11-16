package com.bscalendar.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bscalendar.config.jwt.JwtTokenProvider;
import com.bscalendar.member.dto.MemberDTO;
import com.bscalendar.member.dto.request.LoginRequestDTO;
import com.bscalendar.member.dto.response.TokenResponseDTO;
import com.bscalendar.member.service.MemberDetailsService;
import com.bscalendar.member.service.MemberService;

@Controller
@RequestMapping("/api/member")
public class MemberController {
	/* REST API URL
	 * 회원가입:     POST,    /api/member
	 * 비밀번호찾기: GET,     /api/member/find
	 * 로그인:       GET,     /api/member
	 * 회원정보수정: Put,     /api/member
	 * 회원탈퇴:     DELETE,  /api/member
	 */
	
	@Autowired
	private MemberService memberService;
	
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;
	private final MemberDetailsService memberDetailsService;
	
	public MemberController(
			AuthenticationManager authenticationManager,
			JwtTokenProvider tokenProvider,
			MemberDetailsService memberDetailsService) {
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
		this.memberDetailsService = memberDetailsService;
	}
	
	@PostMapping("/")
	@ResponseBody
	public ResponseEntity<MemberDTO> memberCreate() {
		// TODO: 회원가입
		
		return null;
	}
	
	// Token 생성
	// LocationUrl
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> memberLogin(@RequestBody LoginRequestDTO loginInfo) {
		// TODO: 로그인
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginInfo.getMem_id(), loginInfo.getMem_pwd())
		);
		
		String jwtToken = tokenProvider.createToken(authentication);
		String refreshJwtToken = tokenProvider.createRefreshToken(authentication);
		
		// TODO refreshToken DB Save
		boolean refreshTokenSave = memberService.refreshTokenUpdate(loginInfo.getMem_id(), refreshJwtToken);
		if(!refreshTokenSave) {
			return null;
		}
		
		System.out.println("Token : " + jwtToken);
		System.out.println("Refresh Token : " + refreshJwtToken);
		return ResponseEntity.ok(Map.of(
			"token", new TokenResponseDTO(jwtToken).getToken(),
			"refresh_token", new TokenResponseDTO(refreshJwtToken).getToken(),
			"locationUrl", "/project/list"
		));
	}
	
	@GetMapping("/auth/re")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> refreshToken(@RequestHeader("Authorization") String authorization) {
		// TODO: RefreshToken 검사
		String reToken = authorization.replace("Bearer ", "");
		
		Map<String, Object> response = memberService.refreshToken(reToken, tokenProvider, memberDetailsService);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/find")
	@ResponseBody
	public ResponseEntity<MemberDTO> memberInfoFind() {
		// TODO: 비밀번호찾기
		
		return null;
	}
	
	@PutMapping("/")
	@ResponseBody
	public ResponseEntity<MemberDTO> memberUpdate() {
		// TODO: 회원정보수정
		
		return null;
	}
	
	@DeleteMapping("/")
	@ResponseBody
	public ResponseEntity<MemberDTO> memberDelete() {
		// TODO: 회원탈퇴
		
		return null;
	}
	
}
