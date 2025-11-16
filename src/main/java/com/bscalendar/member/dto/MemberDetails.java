package com.bscalendar.member.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MemberDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final MemberDTO member;
	
	public MemberDetails(MemberDTO member) {
		this.member = member;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole()));
	}

	@Override
	public String getPassword() {
		return member.getMem_pwd();
	}

	@Override
	public String getUsername() {
		return member.getMem_id();
	}
	
	public String getMemberName() {
		return member.getMem_name();
	}
	
	@Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
