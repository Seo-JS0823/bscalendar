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
	// ACCESS TOKEN 유지시간 생성 시간으로부터 30분
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	@Autowired private JwtUtil jwtUtil;
	
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
	    // 토큰에서 아이디 정보 가져와야 함
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
		//mem_pwd = ShaTest.encSHA512(mem_pwd);
		//System.out.println("mem_pwd 로그인 암호후: " + mem_pwd);
		//x = memmapper.loginMember(mem_id, mem_pwd);
		
		// 로그인한 유저의 mem_id로 getMember 메서드를 통해
		// 해당 유저의 DB에 저장된 인코딩된 비밀번호 가져오기
		HashMap<String, Object> targetMember = memmapper.getMember(mem_id);
		
		// HashMap<String, Object> 이므로 get("mem_pwd")해서 Object로 받아오기
		Object targetPassword = targetMember.get("mem_pwd");
		
		// 일치하는지 검사하기 위한 boolean 변수 선언 기본값은 false
		boolean validatePassword = false;
		
		// DB에서 가져온 비밀번호가 실제로 String인지 타입 검사를 마친 후 bCryptPasswordEncoder로
		// matches 함수를 사용해 비밀번호 일치 검사
		if(targetPassword instanceof String) {
			validatePassword = bCryptPasswordEncoder.matches(mem_pwd, (String) targetPassword);
		}
		
		// 비밀번호가 일치하면 int x 값을 1로 바꿈
		if(validatePassword) { x = 1; }
		
		if (x > 0) {
			// 토큰을 생성함
			token = jwtUtil.createJwt(mem_id, "ROLE_USER", ACCESS_TOKEN_EXPIRE_TIME);
		}
		return token;
	}

	
	
}