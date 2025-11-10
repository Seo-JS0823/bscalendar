package com.bscalendar.project.service;

import java.util.List;

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
}
