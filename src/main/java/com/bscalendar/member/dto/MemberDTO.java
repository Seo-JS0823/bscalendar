package com.bscalendar.member.dto;

public class MemberDTO {
/*
MEM_IDX			INT			AutoIncrement	고유 인덱스
MEM_ID			VARCHAR		Not Null, UQ	회원 고유 ID
MEM_PWD			VARCHAR		Not Null		비밀번호
MEM_NAME		VARCHAR		Not Null		회원 이름
MEM_POSITION	VARCHAR		Not Null		직책
MEM_DEPART		VARCHAR		Not Null		부서
MEM_REGDATE		DATETIME	Default Now()	등록 일자
MEM_DEL_FLAG	CHAR		Default N		삭제 여부(Y: 삭제, N: 사용중)
*/
	
	private Integer mem_idx;
	private String mem_id;
	private String mem_pwd;
	private String mem_name;
	private String mem_position;
	private String mem_depart;
	private String mem_regdate;
	private String mem_del_flag;
	
	public MemberDTO() {}

	public MemberDTO(Integer mem_idx,
					 String mem_id,
					 String mem_pwd,
					 String mem_name,
					 String mem_position,
					 String mem_depart,
					 String mem_regdate,
					 String mem_del_flag) {
		
		this.mem_idx = mem_idx;
		this.mem_id = mem_id;
		this.mem_pwd = mem_pwd;
		this.mem_name = mem_name;
		this.mem_position = mem_position;
		this.mem_depart = mem_depart;
		this.mem_regdate = mem_regdate;
		this.mem_del_flag = mem_del_flag;
	}

	public Integer getMem_idx() { return mem_idx; }
	public void setMem_idx(Integer mem_idx) { this.mem_idx = mem_idx; }

	public String getMem_id() { return mem_id; }
	public void setMem_id(String mem_id) { this.mem_id = mem_id; }

	public String getMem_pwd() { return mem_pwd; }
	public void setMem_pwd(String mem_pwd) { this.mem_pwd = mem_pwd; }

	public String getMem_name() { return mem_name; }
	public void setMem_name(String mem_name) { this.mem_name = mem_name; }

	public String getMem_position() { return mem_position; }
	public void setMem_position(String mem_position) { this.mem_position = mem_position; }

	public String getMem_depart() { return mem_depart; }
	public void setMem_depart(String mem_depart) { this.mem_depart = mem_depart; }

	public String getMem_regdate() { return mem_regdate; }
	public void setMem_regdate(String mem_regdate) { this.mem_regdate = mem_regdate; }

	public String getMem_del_flag() { return mem_del_flag; }
	public void setMem_del_flag(String mem_del_flag) { this.mem_del_flag = mem_del_flag; }
	
}
