package com.bscalendar.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bscalendar.project.dto.ProjectDTO;
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
	public ResponseEntity<ProjectDTO> projectCreate(@RequestBody ProjectDTO project) {
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
	public ResponseEntity<List<ProjectDTO>> projectRead(@PathVariable("id") String id) {
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
	public ResponseEntity<ProjectDTO> projectMemberRead(@RequestParam("team_idx") Integer team_idx) {
		// TODO: Service
		System.out.println("TEAM_IDX: " + team_idx);
		return null;
	}
	
	@PutMapping("/")
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
