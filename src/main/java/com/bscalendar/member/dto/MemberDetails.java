package com.bscalendar.member.dto;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.bscalendar.member.dto.MemberDTO;

public class MemberDetails implements UserDetails{
	
	private MemberDTO memberDTO;
	
	public MemberDetails(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}
	
	@Override //권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
	
	// 사용자의 ID 반환
    @Override
    public String getUsername() {
        return memberDTO.getMem_id();
    }
    
	// 사용자의 비밀번호 반환
    @Override
    public String getPassword() {
        return memberDTO.getMem_pwd();
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

   // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

   // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}