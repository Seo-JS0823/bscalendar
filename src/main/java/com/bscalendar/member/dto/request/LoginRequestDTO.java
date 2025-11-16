package com.bscalendar.member.dto.request;

public class LoginRequestDTO {

	private String mem_id;
	private String mem_pwd;
	
	public LoginRequestDTO() {}
	
	public LoginRequestDTO(String mem_id, String mem_pwd) {
		this.mem_id = mem_id;
		this.mem_pwd = mem_pwd;
	}
	
	public String getMem_id() { return mem_id; }
	public void setMem_id(String mem_id) { this.mem_id = mem_id; }
	
	public String getMem_pwd() { return mem_pwd; }
	public void setMem_pwd(String mem_pwd) { this.mem_pwd = mem_pwd; }
	
}
