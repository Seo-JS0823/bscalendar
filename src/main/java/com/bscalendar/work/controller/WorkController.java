package com.bscalendar.work.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bscalendar.work.dto.WorkDTO;
import com.bscalendar.work.mapper.WorkMapper;

@Controller
@RequestMapping("/api/work")
public class WorkController {
	
	@Autowired
	private WorkMapper workMapper;
	/* REST API URL
	 * 업무 생성: POST,     /api/work
	 * 업무 조회: GET,      /api/work
	 * 업무 수정: PUT,      /api/work
	 * 업무 삭제: DELETE,   /api/work
	 */
	
	@PostMapping("/insertWork")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> workCreate(@RequestBody WorkDTO workDTO) {
		// TODO: 업무 생성
		if(workDTO.getWorks_arlam_date().equals("")) {
			workDTO.setWorks_arlam_date(null);
		}
		int work = workMapper.workCreate(workDTO);
		
		Map<String,Object> result = new HashMap<>();
		if( work>0 ) {
			result.put("status","ok");
			result.put("work", workDTO);
			result.put("redirectUrl", "/project/" + workDTO.getTeam_idx());
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("")
	@ResponseBody
	public ResponseEntity<List<WorkDTO>> workRead() {
		// TODO: 업무 조회
		
		return null;
	}
	
	// TODO: works_idx 
	@GetMapping("/list/date/{date}/{team_idx}")
	@ResponseBody
	public ResponseEntity<List<WorkDTO>> dateToWorkRead(
			@PathVariable("date") String date,
			@PathVariable("team_idx") Integer team_idx) {
		
		List<WorkDTO> works = workMapper.findToDateWorks(date, team_idx);
		
		return ResponseEntity.ok(works);
	}
	
	@PatchMapping("/update/{works_idx}/{finFlagChange}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> readUpdate(
			@PathVariable("works_idx") Integer works_idx,
			@PathVariable("finFlagChange") String works_fin_flag
			) {
		// TODO: 멱등성 방어를 위해 SELECT하고 works_fin_flag 확인
		WorkDTO target = workMapper.findWorkToIdx(works_idx);
		if(target == null) {
			return ResponseEntity.badRequest().body(null);
		}
		/*
		if(finFlag.toLowerCase().equals("y")) {
			Map<String, Object> errResponse = Map.of(
				"message", "이미 완료된 업무입니다."
			);
			return ResponseEntity.badRequest().body(errResponse);
		}
		*/
		
		String finFlag = target.getWorks_fin_flag();
		// TODO: 업무 수정
		int workIdx = target.getWorks_idx();
		int updated = workMapper.workUpdate(workIdx, works_fin_flag);
		if(updated < 1) {
			Map<String, Object> notUpdated = Map.of(
				"message", "업무를 업데이트하지 못했습니다."
			);
			return ResponseEntity.badRequest().body(notUpdated);
		}
		
		// TODO: 업데이트된 데이터를 다시 불러와 응답
		WorkDTO updatedSuccess = workMapper.findWorkToIdx(works_idx);
		Map<String, Object> success = Map.of(
			"work", updatedSuccess
		);
		return ResponseEntity.ok(success);
	}
	
	@DeleteMapping("/")
	@ResponseBody
	public ResponseEntity<WorkDTO> readDelete() {
		// TODO: 업무 삭제
		
		return null;
	}
	
}