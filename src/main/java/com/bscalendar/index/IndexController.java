package com.bscalendar.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String login() {
		// TODO: 로그인 페이지
		return "work/work-create";
	}
	
	@GetMapping("/join")
	public String join() {
		// TODO: 회원가입 페이지
		return "";
	}
	
	@GetMapping("/find")
	public String find() {
		// TODO: 아이디 or 비밀번호 찾기 페이지
		return "";
	}
	
}
