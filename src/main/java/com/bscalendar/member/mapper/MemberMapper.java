package com.bscalendar.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bscalendar.member.dto.MemberDTO;

@Mapper
public interface MemberMapper {

	MemberDTO findByUsername(String mem_id);
	
	String findRefreshToken(String mem_id);
	
	int refreshTokenUpdate(String mem_id, String refresh_token);
}
