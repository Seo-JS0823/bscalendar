package com.bscalendar.reply.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlramDTO {

	private int alram_idx;
	private String mem_id;
	private Date alram_date;
	private String title;
	private String message;
	private String read_flag;
	
	public AlramDTO() {}
}
