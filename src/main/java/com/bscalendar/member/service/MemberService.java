package com.bscalendar.member.service;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.bscalendar.jwt.JwtUtil;
import com.bscalendar.jwt.ShaTest;

import lombok.extern.slf4j.Slf4j;
import com.bscalendar.member.mapper.MemberMapper;

@Slf4j
@Service
public class MemberService {

	@Autowired private MemberMapper memmapper;
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	private final JwtUtil jwtUtil = null;
	
	public boolean addMember(JSONObject json) {
		int x = 0;
		String mem_id = null, mem_pwd = null, mem_name = null, mem_position = null, mem_depart = null;
		
		mem_id = json.getString("mem_id");
		mem_pwd = json.getString("mem_pwd");
		//System.out.println("암호전: " + mem_pwd);
		mem_pwd = ShaTest.encSHA512(mem_pwd);
		//System.out.println("암호후: " + mem_pwd);
		mem_name = json.getString("mem_name");
		mem_position = json.getString("mem_position");
		mem_depart = json.getString("mem_depart");
		x = memmapper.addMember(mem_id, mem_pwd, mem_name, mem_position, mem_depart);
		
		return x > 0;
	}
	
	public boolean VariableId(JSONObject json) {
		int x = 0;
		String mem_id = null;
		
		mem_id = json.getString("CheckId");
		x = memmapper.VariableId(mem_id);
		
		return x > 0; // 0 보다 크면 중복임
	}
	
	public HashMap<String, Object> getMember(String mem_id) {
	    mem_id = "test19"; // 토큰에서 아이디 정보 가져와야 함
	    
	    return memmapper.getMember(mem_id);
	}
	
	public boolean updateMember(JSONObject json) {
		int x = 0;
		String mem_id = null, mem_name = null, mem_position = null, mem_depart = null;
		
		mem_id = json.getString("mem_id");
		mem_name = json.getString("mem_name");
		mem_position = json.getString("mem_position");
		mem_depart = json.getString("mem_depart");
		x = memmapper.updateMember(mem_id, mem_name, mem_position, mem_depart);
		
		return x > 0;
	}
	
	public boolean deleteMember(String mem_id) {
		int x = 0;
		x = memmapper.deleteMember(mem_id);
		
		return x > 0;
	}

	public String loginMember(JSONObject json) {
		int x = 0;
		String mem_id = null, mem_pwd = null;
		String token = null;
		
		mem_id = json.getString("mem_id");
		mem_pwd = json.getString("mem_pwd");
		//System.out.println("mem_pwd 로그인 암호전: " + mem_pwd);
		mem_pwd = ShaTest.encSHA512(mem_pwd);
		//System.out.println("mem_pwd 로그인 암호후: " + mem_pwd);
		x = memmapper.loginMember(mem_id, mem_pwd);
		//System.out.println("x : " + x);
		
		if (x > 0) {
			//token = jwtUtil.createJwt(json); Error 토큰 생성 실패!!!
			token = "토큰생성";
		}
		return token;
	}

	
	
}