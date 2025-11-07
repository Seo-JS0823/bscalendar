package com.bscalendar.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bscalendar.project.dto.ProjectDTO;

@Controller
@RequestMapping("/api/project")
public class ProjectController {
	/* REST API URL
	 * 프로젝트 생성: POST,   /api/project
	 * 프로젝트 조회: GET,    /api/project
	 * 프로젝트 수정: PUT,    /api/project
	 * 프로젝트 삭제: DELETE, /api/project
	 */
	
	@GetMapping("/view")
	public String projectView() {
		return "project/project";
	}
	
	@GetMapping("/list/view")
	public String projectListView() {
		return "project/project-list";
	}
	
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<ProjectDTO> projectCreate(@RequestBody ProjectDTO project) {
		// TODO: 프로젝트 생성
		System.out.println(project);
		
		return ResponseEntity.ok(project);
	}
	
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<List<ProjectDTO>> projectRead() {
		// TODO: 프로젝트 조회
		
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
