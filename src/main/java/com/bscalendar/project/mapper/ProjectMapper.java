package com.bscalendar.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bscalendar.project.dto.ProjectDTO;
import com.bscalendar.project.dto.response.MemberWorkDTO;
import com.bscalendar.project.dto.response.ProjectMemberDTO;

@Mapper
public interface ProjectMapper {
	
	// 프로젝트 생성
	public int projectCreate(ProjectDTO project);

	// 프로젝트 조회(EG_MAPP 테이블 INSERT할 IDX)
	public int projectRead_idx(ProjectDTO project);
	
	// 프로젝트 생성
	public int projectCreate_mapp(ProjectDTO project);
	
	// 프로젝트 조회 (Value : mem_id)
	public List<ProjectDTO> projectRead(ProjectDTO project);
	
	// 프로젝트에 소속된 멤버 조회 (param1 : team_idx)
	public List<ProjectMemberDTO> projectMembersRead(Integer team_idx);
	
	// 프로젝트에 소속된 멤버가 등록한 업무 조회 (param1 : member_id, param2: team_idx)
	public List<MemberWorkDTO> projectMemberToWorksRead(String member_id, Integer team_idx);

	public List<MemberWorkDTO> projectDateToWorksRead(Integer teamIdx, String sdate, String edate);
}
