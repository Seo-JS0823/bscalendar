package com.bscalendar.member.controller;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bscalendar.jwt.JwtUtil;
import com.bscalendar.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/member")
public class MemberController {
	/* REST API URL
	 * 회원가입폼:            /api/member/joinForm
	 * 회원가입:     POST,    /api/member/join
	 * 로그인:       GET,     /api/member/login
	 * 회원정보수정: POST,    /api/member/update
	 * 회원탈퇴:     GET,     /api/member/delete
	 * 비밀번호찾기: GET,     /api/member/find
	 */
	
	@Autowired private MemberService memsvc;
	@Autowired private JwtUtil jwtUtil;
		
	@PostMapping("/join")
	public String createMember(HttpServletRequest request, HttpServletResponse response, @RequestBody String rBody) {
		JSONObject json = null, result = new JSONObject("{\"result\":\"error\",\"msg\":\"An error occured.\"}");
		String contentType = null;
		boolean b = false;

		log.info("Requested join from {}",request.getRemoteAddr());
		contentType = request.getContentType();
	
		if(contentType != null && contentType.length() > 0 && contentType.trim().toLowerCase().equals("application/json")){ // content type 가 json 일 경우
			try {
				json = new JSONObject(rBody);
				log.info("Contain request body ::  ",rBody);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			if(json == null) return result.toString();
		}else if(contentType != null && contentType.length() > 0 && contentType.trim().toLowerCase().equals("application/xml")){
			// XML 파싱 모드들이 들어가야 함
		}
		
		b = memsvc.addMember(json);
		if(b) result = new JSONObject("{\"result\":\"ok\"}");
		else result = new JSONObject("{\"result\":\"no\"}");
		
		response.setContentType("application/json");
		response.setHeader("X-exitgreen", "OK!!");
		return result.toString();
	}
	
	@PostMapping("/ajaxIdChk")
	public String postAjaxIdCheck(HttpServletRequest request, HttpServletResponse response, @RequestBody String rBody) {
		// 회원가입 시 아이디 중복체크
		JSONObject json = null, result = new JSONObject("{\"result\":\"error\", \"msg\":\"An error occured.\"}");
		boolean b = false;
		
		try {
			json = new JSONObject(rBody);
		} catch(Exception e) {
			log.error(e.getMessage());
		}
		
		b = memsvc.VariableId(json);
		if(!b) result = new JSONObject("{\"result\":\"success\"}");
		else response.setStatus(401);
		
		return result.toString();
	}
	
	@GetMapping("/getMember")
	public String getMember(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = null, result = new JSONObject("{\"result\":\"error\",\"msg\":\"An error occured.\"}");
		// String token = (String) request.getAttribute("token");
		String token = request.getHeader("Authorization").substring(7);
		String mem_id = jwtUtil.getUsername(token);
		HashMap<String, Object> member = memsvc.getMember(mem_id);
		
		try {
			json = new JSONObject(member);
		} catch (Exception e) {
			response.setStatus(401);
			return result.toString();
		}
		
		if (json != null) {
			result = new JSONObject("{\"result\":\"success\"}");
		}
		return json.toString();
	}
	
	@PostMapping("/update")
	public String updateMember(HttpServletRequest request, HttpServletResponse response, @RequestBody String rBody) {
		JSONObject json = null, result = new JSONObject("{\"result\":\"error\",\"msg\":\"An error occured.\"}");
		boolean b = false;
		
		try {
			json = new JSONObject(rBody);
		} catch (Exception e) {
			response.setStatus(401);
			return result.toString();
		}
		b = memsvc.updateMember(json);
		
		if(b) result = new JSONObject("{\"result\":\"ok\"}");
		else response.setStatus(401);
		
		return result.toString();
	}
	
	@DeleteMapping("/delete") 
	public void deleteMember(HttpServletResponse response, HttpServletRequest request) {
		/* 토큰에서 userId 가져오기 */
		String token = (String) request.getAttribute("token");
		jwtUtil.getUsername(token);
		
	}
	
	@PostMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, @RequestBody String rBody) {
		JSONObject json = null, result = new JSONObject("{\"result\":\"error\", \"msg\":\"An error occured.\"}");
		String token = null;
		try {
			json = new JSONObject(rBody);
		} catch (Exception e) {
			response.setStatus(401);
			return response.toString();
		}
		if(json == null) return result.toString();
		token = memsvc.loginMember(json);
		
		/*if(token != null){
			response.setHeader("Authentication", token);
			result = new JSONObject("{\"result\":\"ok\"}");
		} else{
			result = new JSONObject("{\"result\":\"fail\"}");
		}
		
		response.setContentType("application/json");
		return result.toString();*/
		
		// token이 null이 아니면 생성되었다는 뜻이므로 응답 파라미터로 token을 추가하여
		// 생성한 토큰을 넣음. JS 에서는 data.token을 localStorage에 setItem 하여 넣음.
		if(token != null){
			result = new JSONObject("{\"result\":\"ok\", \"token\":\"" + token + "\"}");
		} else{
			result = new JSONObject("{\"result\":\"fail\"}");
		}
		return result.toString();
	}	
}