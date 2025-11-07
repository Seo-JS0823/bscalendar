package com.bscalendar.project.dto;

public class ProjectDTO {
/*
TEAM_IDX		INT			PK				고유 인덱스
TEAM_NAME		VARCHAR						프로젝트명
TEAM_SDATE		VARCHAR						프로젝트 시작일
TEAM_EDATE		VARCHAR						프로젝트 종료일
MEM_ID			VARCHAR		FK				프로젝트 만든사람
TEAM_CON_FLAG	CHAR		DEFAULT 'N'		프로젝트 완료 여부
TEAM_REGDATE	DATETIME	DEFAULT NOW()	프로젝트 팀 생성일자
TEAM_DEL_FLAG	CHAR		DEFAULT 'N'		프로젝트 삭제 여부
 */
	
	private Integer team_idx;
	private String team_name;
	private String team_sdate;
	private String team_edate;
	private String mem_id;
	private String team_con_flag;
	private String team_regdate;
	private String team_del_flag;
	
	public ProjectDTO() {}

	public ProjectDTO(Integer team_idx, String team_name, String team_sdate, String team_edate, String mem_id,
			String team_con_flag, String team_regdate, String team_del_flag) {
		super();
		this.team_idx = team_idx;
		this.team_name = team_name;
		this.team_sdate = team_sdate;
		this.team_edate = team_edate;
		this.mem_id = mem_id;
		this.team_con_flag = team_con_flag;
		this.team_regdate = team_regdate;
		this.team_del_flag = team_del_flag;
	}

	public Integer getTeam_idx() {
		return team_idx;
	}

	public void setTeam_idx(Integer team_idx) {
		this.team_idx = team_idx;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_Name(String team_name) {
		this.team_name = team_name;
	}

	public String getTeam_sdate() {
		return team_sdate;
	}

	public void setTeam_sdate(String team_sdate) {
		this.team_sdate = team_sdate;
	}

	public String getTeam_edate() {
		return team_edate;
	}

	public void setTeam_edate(String team_edate) {
		this.team_edate = team_edate;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getTeam_con_flag() {
		return team_con_flag;
	}

	public void setTeam_con_flag(String team_con_flag) {
		this.team_con_flag = team_con_flag;
	}

	public String getTeam_regdate() {
		return team_regdate;
	}

	public void setTeam_regdate(String team_regdate) {
		this.team_regdate = team_regdate;
	}

	public String getTeam_del_flag() {
		return team_del_flag;
	}

	public void setTeam_del_flag(String team_del_flag) {
		this.team_del_flag = team_del_flag;
	}
	
}
