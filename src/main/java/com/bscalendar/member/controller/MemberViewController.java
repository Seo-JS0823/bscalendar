package com.bscalendar.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberViewController {

	@GetMapping("/")
	public String loginForm() {
		return "member/login";
	}
	
	@GetMapping("/join")
	public String joinForm() {
		return "member/join";
	}
	
	@GetMapping("/find")
	public String findForm() {
		return "member/find";
	}
	
	@GetMapping("/mypage")
	public String myPageForm() {
		return "member/mypage";
	}
}
