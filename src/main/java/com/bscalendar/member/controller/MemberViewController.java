package com.bscalendar.member.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bscalendar.member.dto.MemberDTO;
import com.bscalendar.member.service.MemberService;

@Controller
public class MemberViewController {
	
	@Autowired private MemberService memsvc;
	
	@GetMapping("/joinForm")
	public String memberForm() {
		return "member/joinForm";
	}
	
	@GetMapping("/")
	public String loginForm() {
		return "member/loginForm";
	}
	
	@GetMapping("/member/mypage")
	public String mypage() {		
		return "member/mypage";
	}
}