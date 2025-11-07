package com.bscalendar.work.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bscalendar.work.dto.WorkDTO;

@Controller
@RequestMapping("/api/work")
public class WorkController {
	/* REST API URL
	 * 업무 생성: POST,     /api/work
	 * 업무 조회: GET,      /api/work
	 * 업무 수정: PUT,      /api/work
	 * 업무 삭제: DELETE,   /api/work
	 */
	
	@PostMapping("/")
	@ResponseBody
	public ResponseEntity<WorkDTO> workCreate() {
		// TODO: 업무 생성
		
		return null;
	}
	
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<List<WorkDTO>> workRead() {
		// TODO: 업무 조회
		
		return null;
	}
	
	@PutMapping("/")
	@ResponseBody
	public ResponseEntity<WorkDTO> readUpdate() {
		// TODO: 업무 수정
		
		return null;
	}
	
	@DeleteMapping("/")
	@ResponseBody
	public ResponseEntity<WorkDTO> readDelete() {
		// TODO: 업무 삭제
		
		return null;
	}
	
}