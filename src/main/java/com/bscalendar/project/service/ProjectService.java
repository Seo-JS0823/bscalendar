package com.bscalendar.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bscalendar.member.dto.MemberDTO;
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
		int created = projectMapper.projectCreate(project);
		if(created > 0) {
			int mapp_idx = projectMapper.projectRead_idx(project);
			project.setTeam_idx(mapp_idx);
			
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
	public List<ProjectMemberDTO> projectMemberAll() {
		List<ProjectMemberDTO> projectMemberAll = projectMapper.projectMemberAll();
		return projectMemberAll;
	}

	@Transactional
	public Map<String, Object> projectMemberAdd(List<String> memberIds, Integer team_idx) {
		// TODO: memberIds 배열에서 멤버 ID를 하나씩 추출해서 INSERT문 날리기 + 트랜잭션
		// TODO: EG_MAPP에 team_idx + mem_id 같은 값 Key가 적용되어 있지 않으므로 전체를 들고와 비교할 것
		List<ProjectMemberDTO> projectTeamMemberValid = projectMapper.projectSosocMember(team_idx);
		System.out.println(projectTeamMemberValid);
		// TODO: 중복 방지
		for(int i = 0; i < projectTeamMemberValid.size(); i++) {
			String sosocId = projectTeamMemberValid.get(i).getMem_id();
			String delFlag = projectTeamMemberValid.get(i).getMapp_del_flag();
			for(int j = 0; j < memberIds.size(); j++) {
				String targetId = memberIds.get(j);
				if(sosocId.equals(targetId) && delFlag.toLowerCase().equals("n")) {
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
		
		return Map.of(
			"successMessage", response + " 멤버 총" + targetSize + "명 투입되었습니다."
		);
	}
	
}
