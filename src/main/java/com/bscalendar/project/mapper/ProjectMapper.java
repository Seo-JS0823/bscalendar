package com.bscalendar.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bscalendar.member.dto.MemberDTO;
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

	// 달력에 클릭한 날짜로 소속된 프로젝트의 등록된 업무 가져오기
	// 팀 공유된 업무만 들고옴
	public List<MemberWorkDTO> projectDateToWorksRead(Integer teamIdx, String sdate, String edate);

	// 멤버를 검색하기위해 MEM_NAME, MEM_POSITION, MEM_DEPART를 검색 조건으로
	// 검색어를 이용해 멤버를 조회함
	public List<ProjectMemberDTO> projectMembersSearch(String search);

	// 프로젝트에 소속된 인원을 제외하고 멤버 리스트를 조회함
	public List<ProjectMemberDTO> projectMemberAll(Integer team_idx);

	// 멤버 아이디로 멤버 아이디와 멤버 이름을 조회함
	public ProjectMemberDTO memberToMemId(String id);

	// 프로젝트에 멤버를 추가함
	// 대상 테이블은 EG_MAPP
	public int projectMemberAdd(ProjectMemberDTO projectAddTarget);

	// 프로젝트에 소속되어 있는 멤버를 조회함
	public List<ProjectMemberDTO> projectSosocMember(Integer team_idx);

	// 프로젝트에 대한 기본 정보를 가져옴
	public ProjectDTO projectSettingRead(Integer team_idx);

	// 프로젝트의 기본 정보를 업데이트함
	public int projectSettingUpdate(Integer team_idx, String team_edate, String team_name);

	// 프로젝트를 종료 처리함
	public int projectDelete(Integer team_idx);

	// 프로젝트에서 멤버를 추방함
	public int projectMemberDelete(Integer team_idx, String mem_id);

	public List<MemberWorkDTO> hideWorks(Integer team_idx, String mem_id);

	public List<MemberWorkDTO> nohideCalendar(String sdate, String edate, Integer team_idx);

	public List<MemberWorkDTO> hideCalendar(String sdate, String edate, Integer team_idx, String mem_id);

	public List<MemberWorkDTO> dateToHideWorks(String nowDate, Integer team_idx, String mem_id);

	public List<MemberWorkDTO> dateToNoHideWorks(String nowDate, Integer team_idx);
}
