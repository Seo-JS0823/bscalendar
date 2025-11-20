package com.bscalendar.jwt;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SecurityUser implements UserDetails{

	private boolean accountNonExpired = true;  		// 계정 만료 여부
	private boolean accountNonLocked = true;   		// 사용자 계정의 lock여부
	private boolean credentialsNonExpired = true; 	// Credentials(Password)의 만료여부
	private boolean enabled = true; 				// 계정 활성화 여부
	
	private String userId;
	private String pw;
	private String name;
	private String role;

	public SecurityUser(){}
	public SecurityUser(String id, String pw){ 
		this.userId = id;
		this.pw = pw; 
	}

	@Override public String getPassword() { return pw; }
	@Override public String getUsername() { return userId; }
	@Override public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(new SimpleGrantedAuthority("ROLE_USER")); }
}