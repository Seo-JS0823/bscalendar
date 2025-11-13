package com.bscalendar.project.dto.response;

public class ProjectMemberDTO {
	
	private String mem_name;
	private String mem_id;
	
	public ProjectMemberDTO() {}
	
	public ProjectMemberDTO(String mem_name, String mem_id) {
		this.mem_name = mem_name;
		this.mem_id = mem_id;
	}
	
	public String getMem_name() { return mem_name; }
	public void setMem_name(String mem_name) { this.mem_name = mem_name; }
	
	public String getMem_id() { return mem_id; }
	public void setMem_id(String mem_id) { this.mem_id = mem_id; }
}
