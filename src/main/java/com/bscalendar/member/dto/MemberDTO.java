package com.bscalendar.member.dto;

import lombok.Data;

@Data
public class MemberDTO {

	private int mem_idx;
	private String mem_id;
	private String mem_pwd;
	private String mem_name;
	private String mem_position;
	private String mem_depart;
	private String mem_del_flag;
}