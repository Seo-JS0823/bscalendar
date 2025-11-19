package com.bscalendar.project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bscalendar.project.dto.ProjectDTO;
import com.bscalendar.project.dto.response.MemberWorkDTO;
import com.bscalendar.project.dto.response.ProjectMemberDTO;
import com.bscalendar.project.mapper.ProjectMapper;

@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	
	// 프로젝트 생성: Controller [POST: projectCreate Method]
	// SQL 3번 날라감
	@Transactional
	public boolean projectCreate(ProjectDTO project) {
		
		// 1. EG_TEAM에 프로젝트를 등록함
		int created = projectMapper.projectCreate(project);
		if(created > 0) {
			// 2. EG_TEAM에 정상적으로 등록되었는지 조회하여 확인함
			int mapp_idx = projectMapper.projectRead_idx(project);
			project.setTeam_idx(mapp_idx);
			
			// 3. 정상적으로 등록되었으면 EG_MAPP 매핑 테이블에도 정보를 등록함
			int mapp_created = projectMapper.projectCreate_mapp(project);
			if(mapp_created > 0) {
				return true;
			}
		}
		return false;
	}

	// 프로젝트 조회: Controller [GET: projectRead Method]
	public List<ProjectDTO> projectRead(ProjectDTO project) {
		List<ProjectDTO> projectList = projectMapper.projectRead(project);
		int size = projectList.size();
		
		if(size == 0) {
			return null;
		}
		
		return projectList;
	}
	
	// 프로젝트에 소속된 멤버 조회: Controller [GET: projectMemberRead Method]
	public List<ProjectMemberDTO> projectMemberRead(Integer team_idx) {
		List<ProjectMemberDTO> members = projectMapper.projectMembersRead(team_idx);
		return members;
	}
	
	// 프로젝트에 소속된 멤버가 등록한 업무 리스트 조회: Controller [GET: projectMemberWorkRead Method]
	public List<MemberWorkDTO> projectMemberWorkRead(String member_id, Integer team_idx) {
		List<MemberWorkDTO> memberToWorks = projectMapper.projectMemberToWorksRead(member_id, team_idx);
		return memberToWorks;
	}

	// 달력을 클릭했을 때 클릭한 날짜에 해당하는 업무 리스트 가져오기
	public List<MemberWorkDTO> dateToWorks(Integer teamIdx, String sdate, String edate) {
		List<MemberWorkDTO> dateToWorks = projectMapper.projectDateToWorksRead(teamIdx, sdate, edate);
		return dateToWorks;
	}
	
	// 멤버를 조회하는 메소드
	public List<ProjectMemberDTO> projectMembersSearch(String search) {
		List<ProjectMemberDTO> searchToMembers = projectMapper.projectMembersSearch(search);
		return searchToMembers;
	}
	
	// 전체 멤버 조회
	public List<ProjectMemberDTO> projectMemberAll(Integer team_idx) {
		List<ProjectMemberDTO> projectMemberAll = projectMapper.projectMemberAll(team_idx);
		return projectMemberAll;
	}

	@Transactional
	public Map<String, Object> projectMemberAdd(List<String> memberIds, Integer team_idx) {
		// TODO: memberIds 배열에서 멤버 ID를 하나씩 추출해서 INSERT문 날리기 + 트랜잭션
		// TODO: EG_MAPP에 team_idx + mem_id 같은 값 Key가 적용되어 있지 않으므로 전체를 들고와 비교할 것
		List<ProjectMemberDTO> projectTeamMemberValid = projectMapper.projectSosocMember(team_idx);
		
		// TODO: 중복 방지를 위한 로직
		for(int i = 0; i < projectTeamMemberValid.size(); i++) {
			String sosocId = projectTeamMemberValid.get(i).getMem_id();
			String delFlag = projectTeamMemberValid.get(i).getMapp_del_flag();
			for(int j = 0; j < memberIds.size(); j++) {
				String targetId = memberIds.get(j);
				if(sosocId.equals(targetId)) {
					return Map.of(
						"errorMessage", targetId + " 님은 이미 등록되어 있는 사람입니다."
					);
				}
			}
		}
		
		String response = "";
		int targetSize = 0;
		for(int i = 0; i < memberIds.size(); i++) {
			String id = memberIds.get(i);
			ProjectMemberDTO projectAddTarget = projectMapper.memberToMemId(id);
			projectAddTarget.setTeam_idx(team_idx);
			
			int inserted = projectMapper.projectMemberAdd(projectAddTarget);
			if(inserted > 0) {
				response += projectAddTarget.getMem_name() + ", ";
				targetSize++;
			}
		}
		
		// 등록이 완료되면 (멤버이름) 멤버 총 (몇) 명 투입되었습니다. 메시지를 보냄
		return Map.of(
			"successMessage", response + " 멤버 총" + targetSize + "명 투입되었습니다."
		);
	}

	// 프로젝트의 기본 정보를 조회합니다.
	public ProjectDTO projectSettingRead(Integer team_idx) {
		ProjectDTO targetProject = projectMapper.projectSettingRead(team_idx);
		return targetProject;
	}

	// 프로젝트의 설정을 변경합니다. 변경이 정상적으로 되었으면 true를 반환합니다.
	public boolean projectSettingUpdate(Integer team_idx, String team_edate, String team_name) {
		int projectUpdated = projectMapper.projectSettingUpdate(team_idx, team_edate, team_name);
		return projectUpdated > 0;
	}

	// 프로젝트를 종료합니다. 정상적으로 처리되었으면 true를 반환합니다.
	public boolean projectDelete(Integer team_idx) {
		int projectDeleted = projectMapper.projectDelete(team_idx);
		return projectDeleted > 0;
	}

	// return null: 멤버가 정상적으로 추방되지 않습니다.
	// return memberName : 멤버가 정상적으로 추방되고 타깃의 성함을 리턴합니다.
	public String projectMemberDelete(Integer team_idx, String mem_id) {
		int projectMemberDelete = projectMapper.projectMemberDelete(team_idx, mem_id);
		String memberName = projectMapper.memberToMemId(mem_id).getMem_name();
		if(projectMemberDelete == 0) {
			return null;
		}
		return memberName;
	}

	// 비공유 업무 가져오기
	public List<MemberWorkDTO> getHideWorks(Integer team_idx, String mem_id) {
		List<MemberWorkDTO> hideWorks = projectMapper.hideWorks(team_idx, mem_id);
		return hideWorks;
	}

	public List<MemberWorkDTO> calendarToDateWorks(String sdate, String edate, Integer team_idx, String mem_id) {
		// TODO: 공유 업무
		List<MemberWorkDTO> nohides = projectMapper.nohideCalendar(sdate, edate, team_idx);
		
		// TODO: 비공유 업무
		List<MemberWorkDTO> hides = projectMapper.hideCalendar(sdate, edate, team_idx, mem_id);
		
		// TODO: List 병합
		List<MemberWorkDTO> toMerges = new ArrayList<>();
		Collections.addAll(toMerges, nohides.toArray(new MemberWorkDTO[0]));
		Collections.addAll(toMerges, hides.toArray(new MemberWorkDTO[0]));
		
		return toMerges;
	}

	public List<MemberWorkDTO> worksListToDate(String nowDate, Integer team_idx, String mem_id) {
		// TODO: 공유 업무
		List<MemberWorkDTO> dateToNoHideWorks = projectMapper.dateToNoHideWorks(nowDate, team_idx);
		
		// TODO: 비공유 업무
		List<MemberWorkDTO> dateToHideWorks = projectMapper.dateToHideWorks(nowDate, team_idx, mem_id);
		
		// TODO: List 병합
		List<MemberWorkDTO> toMerges = new ArrayList<>();
		Collections.addAll(toMerges, dateToHideWorks.toArray(new MemberWorkDTO[0]));
		Collections.addAll(toMerges, dateToNoHideWorks.toArray(new MemberWorkDTO[0]));
		
		return toMerges;
	}
	
}
