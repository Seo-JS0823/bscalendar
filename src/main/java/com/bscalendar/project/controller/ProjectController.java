package com.bscalendar.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bscalendar.project.dto.ProjectDTO;
import com.bscalendar.project.dto.response.MemberWorkDTO;
import com.bscalendar.project.dto.response.ProjectMemberDTO;
import com.bscalendar.project.service.ProjectService;

@Controller
@RequestMapping("/api/project")
public class ProjectController {
	/* REST API URL
	 * 프로젝트 생성: POST,   /api/project
	 * 프로젝트 조회: GET,    /api/project
	 * 프로젝트 수정: PUT,    /api/project
	 * 프로젝트 삭제: DELETE, /api/project
	 */
	
	@Autowired
	private ProjectService projectSvc;
	
	// 프로젝트 생성 컨트롤러
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<ProjectDTO> projectCreate(
			@RequestBody ProjectDTO project) {
		
		// Service
		boolean created = projectSvc.projectCreate(project);
		if(created) {
			return ResponseEntity.status(HttpStatus.OK).body(project);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	// ID 값으로 조회한 프로젝트 조회 컨트롤러
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<List<ProjectDTO>> projectRead(
			@PathVariable("id") String id) {
		
		ProjectDTO target = new ProjectDTO();
		target.setMem_id(id);
		// Service
		List<ProjectDTO> projectList = projectSvc.projectRead(target);
		if(projectList != null) {
			return ResponseEntity.ok(projectList);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	// EG_MAPP에 TEAM_IDX에 해당하는 인원의 멤버 이름을 조회하는 컨트롤러
	@GetMapping("/members/{team_idx}")
	@ResponseBody
	public ResponseEntity<List<ProjectMemberDTO>> projectMemberRead(
			@PathVariable("team_idx") Integer team_idx) {
		
		// TODO: team_idx Null Check
		if(team_idx == null) {
			return ResponseEntity.badRequest().body(null);
		}
		
		// TODO: Service, team_idx로 프로젝트 내 멤버 리스트 조회
		List<ProjectMemberDTO> members = projectSvc.projectMemberRead(team_idx);
		
		return ResponseEntity.ok(members);
	}
	
	// 멤버가 프로젝트 내에서 쓴 업무 리스트를 조회하는 컨트롤러
	@GetMapping("/members/work/list/{member_id}/{team_idx}")
	@ResponseBody
	public ResponseEntity<List<MemberWorkDTO>> projectMemberWorkRead(
			@PathVariable("member_id") String member_id,
			@PathVariable("team_idx") Integer team_idx) {
		
		// TODO: team_idx와 member_id에 해당하는 work 리스트 가져오기
		List<MemberWorkDTO> memberToWorks = projectSvc.projectMemberWorkRead(member_id, team_idx);
		System.out.println(memberToWorks);
		return ResponseEntity.ok(memberToWorks);
	}
	
	// team_idx, sdate, edate로 업무조회 / 공유 업무만
	@GetMapping("/work/list/{teamIdx}/{sdate}/{edate}")
	@ResponseBody
	public ResponseEntity<List<MemberWorkDTO>> dateToWorkList(
			@PathVariable("teamIdx") Integer teamIdx,
			@PathVariable("sdate") String sdate,
			@PathVariable("edate") String edate) {
		
		// TODO: Service 호출
		List<MemberWorkDTO> dateToWorks = projectSvc.dateToWorks(teamIdx, sdate, edate);
		return ResponseEntity.ok(dateToWorks);
	}
	
	@PatchMapping("/")
	@ResponseBody
	public ResponseEntity<ProjectDTO> projectUpdate() {
		// TODO: 프로젝트 수정
		
		return null;
	}
	
	@DeleteMapping("/")
	@ResponseBody
	public ResponseEntity<ProjectDTO> projectDelete() {
		// TODO: 프로젝트 삭제
		
		return null;
	}
}
