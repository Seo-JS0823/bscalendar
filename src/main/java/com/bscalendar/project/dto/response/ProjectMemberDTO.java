package com.bscalendar.project.dto.response;

public class ProjectMemberDTO {
	
	private Integer mem_idx;
	private Integer team_idx;
	private String mem_name;
	private String mem_id;
	private String mem_position;
	private String mem_depart;
	private String mapp_del_flag;
	
	public ProjectMemberDTO() {}
	
	public ProjectMemberDTO(Integer mem_idx, Integer team_idx, String mem_name, String mem_id, String mem_position, String mem_depart
			, String mapp_del_flag) {
		this.mem_idx = mem_idx;
		this.team_idx = team_idx;
		this.mem_name = mem_name;
		this.mem_id = mem_id;
		this.mem_position = mem_position;
		this.mem_depart = mem_depart;
		this.mapp_del_flag = mapp_del_flag;
	}

	public Integer getMem_idx() { return mem_idx; }
	public void setMem_idx(Integer mem_idx) { this.mem_idx = mem_idx; }
	
	public Integer getTeam_idx() { return team_idx; }
	public void setTeam_idx(Integer team_idx) { this.team_idx = team_idx; }
	
	public String getMem_name() { return mem_name; }
	public void setMem_name(String mem_name) { this.mem_name = mem_name; }
	
	public String getMem_id() { return mem_id; }
	public void setMem_id(String mem_id) { this.mem_id = mem_id; }
	
	public String getMem_position() { return mem_position; }
	public void setMem_position(String mem_position) { this.mem_position = mem_position; }
	
	public String getMem_depart() { return mem_depart; }
	public void setMem_depart(String mem_depart) { this.mem_depart = mem_depart; }
	
	public String getMapp_del_flag() { return mapp_del_flag; }
	public void setMapp_del_flag(String mapp_del_flag) { this.mapp_del_flag = mapp_del_flag; }

	@Override
	public String toString() {
		return "ProjectMemberDTO [mem_idx=" + mem_idx + ", team_idx=" + team_idx + ", mem_name=" + mem_name
				+ ", mem_id=" + mem_id + ", mem_position=" + mem_position + ", mem_depart=" + mem_depart
				+ ", mapp_del_flag=" + mapp_del_flag + "]";
	}
}
