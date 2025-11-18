package com.bscalendar.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bscalendar.member.service.MemberService;

@Controller
public class MemberViewController {
	
	@Autowired private MemberService memsvc;
	
	@GetMapping("/joinForm")
	public String memberForm() {
		return "member/joinForm";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
	
	@GetMapping("/index")
	public String main() {
		return "index";
	}
	
	@GetMapping("/member/mypage")
	public String mypage() {		
		return "member/mypage";
	}
}