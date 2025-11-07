package com.bscalendar.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bscalendar.member.dto.MemberDTO;

@Controller
@RequestMapping("/api/member")
public class MemberController {
	/* REST API URL
	 * 회원가입폼:            /api/member/joinForm
	 * 회원가입:     POST,    /api/member/join
	 * 비밀번호찾기: GET,     /api/member/find
	 * 로그인:       GET,     /api/member/login
	 * 회원정보수정: Put,     /api/member/update
	 * 회원탈퇴:     DELETE,  /api/member/quit
	 */
	
	@GetMapping("/joinForm")
	public String memberForm() {
		return "member/joinForm";
	}
	
	@PostMapping("/join")
	@ResponseBody
	public ResponseEntity<MemberDTO> memberCreate() {
		// TODO: 회원가입
		
		return null;
	}
	
	@GetMapping("/login")
	@ResponseBody
	public ResponseEntity<MemberDTO> memberLogin() {
		// TODO: 로그인
		
		return null;
	}
	
	@GetMapping("/find")
	@ResponseBody
	public ResponseEntity<MemberDTO> memberInfoFind() {
		// TODO: 비밀번호찾기
		
		return null;
	}
	
	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<MemberDTO> memberUpdate() {
		// TODO: 회원정보수정
		
		return null;
	}
	
	@DeleteMapping("/quit")
	@ResponseBody
	public ResponseEntity<MemberDTO> memberDelete() {
		// TODO: 회원탈퇴
		
		return null;
	}
	
}
