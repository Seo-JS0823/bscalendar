package com.bscalendar.work.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDTO {
	private Integer works_idx;
	private Integer team_idx;
	private String  mem_id;
	private String  works_comment;
	private String  works_hide;
	private String  works_arlam;
	private String  works_arlam_date;
	private String  works_arlam_conf;
	private String  works_sdate;
	private String  works_edate;
	private String  workd_fin_flag;
	private String  works_del_flag;
}
