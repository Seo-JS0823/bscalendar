package com.bscalendar.member.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bscalendar.member.dto.MemberDTO;
import com.bscalendar.member.dto.MemberDetails;
import com.bscalendar.member.mapper.MemberMapper;

@Service
public class MemberDetailsService implements UserDetailsService {
	
	private final MemberMapper memberMapper;
	
	public MemberDetailsService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String mem_id) throws UsernameNotFoundException {
		MemberDTO member = memberMapper.findByUsername(mem_id);
		if(member == null) {
			throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
		}
		return new MemberDetails(member);
	}
}
