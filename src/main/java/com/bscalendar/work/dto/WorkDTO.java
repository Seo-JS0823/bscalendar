package com.bscalendar.work.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDTO {
/*
EG_WORKS 업무 테이블
WORKS_IDX			INT			AutoIncrement
TEAM_IDX			INT			Not Null		EG_TEAM.TEAM_IDX(FK)
MEM_ID				VARCHAR		Not Null		EG_MEMBER.MEM_ID(FK)
WORKS_COMMENT		VARCHAR		Not Null
WORKS_HIDE			CHAR		Default Y
WORKS_ARLAM			CHAR		Default N
WORKS_ALRAM_DATE	DATETIME	Default Null
WORKS_ARLAM_CONF	CHAR		Default N
WORKS_SDATE			DATETIME
WORKS_EDATE			DATETIME
WORKS_REGDATE		DATETIME	Default Now()
WORKS_FIN_FLAG		CHAR		Default N
WORKS_DEL_FLAG		CHAR		Default N
 */
	private Integer works_idx;
	private Integer team_idx;
	private String  team_name;
	private String  team_regdate;
	private String  mem_id;
	private String  mem_name;
	private String  works_comment;
	private String  works_hide;
	private String  works_arlam;
	private String  works_arlam_date;
	private String  works_arlam_conf;
	private String  works_sdate;
	private String  works_edate;
	private String  works_fin_flag;
	private String  works_del_flag;
}
