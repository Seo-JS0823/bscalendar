package com.bscalendar.project.dto.response;

public class MemberWorkDTO {
	private Integer works_idx;
	private Integer team_idx;
	private String mem_id;
	private String mem_name;
	private String works_comment;
	private String works_hide;
	private String works_arlam;
	private String works_alram_date;
	private String works_arlam_conf;
	private String works_sdate;
	private String works_edate;
	private String works_regdate;
	private String works_fin_flag;
	private String works_del_flag;
	
	public MemberWorkDTO() {}
	
	public MemberWorkDTO(
			Integer works_idx,
			Integer team_idx,
			String mem_id,
			String mem_name,
			String works_comment,
			String works_hide,
			String works_arlam,
			String works_alram_date,
			String works_arlam_conf,
			String works_sdate,
			String works_edate,
			String works_regdate,
			String works_fin_flag,
			String works_del_flag) {
		
		this.works_idx = works_idx;
		this.team_idx = team_idx;
		this.mem_id = mem_id;
		this.mem_name = mem_name;
		this.works_comment = works_comment;
		this.works_hide = works_hide;
		this.works_arlam = works_arlam;
		this.works_alram_date = works_alram_date;
		this.works_arlam_conf = works_arlam_conf;
		this.works_sdate = works_sdate;
		this.works_edate = works_edate;
		this.works_regdate = works_regdate;
		this.works_fin_flag = works_fin_flag;
		this.works_del_flag = works_del_flag;
	}

	public Integer getWorks_idx() { return works_idx; }
	public void setWorks_idx(Integer works_idx) { this.works_idx = works_idx; }

	public Integer getTeam_idx() { return team_idx; }
	public void setTeam_idx(Integer team_idx) { this.team_idx = team_idx; }

	public String getMem_id() { return mem_id; }
	public void setMem_id(String mem_id) { this.mem_id = mem_id; }

	public String getMem_name() { return mem_name; }
	public void setMem_name(String mem_name) { this.mem_name = mem_name; }
	
	public String getWorks_comment() { return works_comment; }
	public void setWorks_comment(String works_comment) { this.works_comment = works_comment; }

	public String getWorks_hide() { return works_hide; }
	public void setWorks_hide(String works_hide) { this.works_hide = works_hide; }

	public String getWorks_arlam() { return works_arlam; }
	public void setWorks_arlam(String works_arlam) { this.works_arlam = works_arlam; }

	public String getWorks_alram_date() { return works_alram_date; }
	public void setWorks_alram_date(String works_alram_date) { this.works_alram_date = works_alram_date; }

	public String getWorks_arlam_conf() { return works_arlam_conf; }
	public void setWorks_arlam_conf(String works_arlam_conf) { this.works_arlam_conf = works_arlam_conf; }
	
	public String getWorks_sdate() { return works_sdate; }
	public void setWorks_sdate(String works_sdate) { this.works_sdate = works_sdate; }
	
	public String getWorks_edate() { return works_edate; }
	public void setWorks_edate(String works_edate) { this.works_edate = works_edate; }

	public String getWorks_regdate() { return works_regdate; }
	public void setWorks_regdate(String works_regdate) { this.works_regdate = works_regdate; }

	public String getWorks_fin_flag() { return works_fin_flag; }
	public void setWorks_fin_flag(String works_fin_flag) { this.works_fin_flag = works_fin_flag; }

	public String getWorks_del_flag() { return works_del_flag; }
	public void setWorks_del_flag(String works_del_flag) { this.works_del_flag = works_del_flag; }

	@Override
	public String toString() {
		return "MemberWorkDTO [works_idx=" + works_idx + ", team_idx=" + team_idx + ", mem_id=" + mem_id
				+ ", works_comment=" + works_comment + ", works_hide=" + works_hide + ", works_arlam=" + works_arlam
				+ ", works_alram_date=" + works_alram_date + ", works_arlam_conf=" + works_arlam_conf + ", works_sdate="
				+ works_sdate + ", works_edate=" + works_edate + ", works_regdate=" + works_regdate
				+ ", works_fin_flag=" + works_fin_flag + ", works_del_flag=" + works_del_flag + "]";
	}
	
}
