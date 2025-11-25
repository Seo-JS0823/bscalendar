package com.bscalendar.work.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bscalendar.project.service.ProjectService;
import com.bscalendar.work.dto.WorkDTO;
import com.bscalendar.work.mapper.WorkMapper;
import com.bscalendar.work.service.WorkService;

@Controller
@RequestMapping("/api/work")
public class WorkController {
	
	//알람기능을 위한 추가
	@Autowired
	private WorkService workService;
	
	@Autowired
	private ProjectService projectService;
	
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
		if(workDTO.getWorks_arlam_date() != null && workDTO.getWorks_arlam_date().equals("")) {
			workDTO.setWorks_arlam_date(null);
		}
		
		// 1. DB 저장
		int work = workMapper.workCreate(workDTO);
		
		Map<String,Object> result = new HashMap<>();
		
		if( work > 0 ) {
			
			// 알림 발송 
			// DB 저장이 성공했을 때만 실행됨. 에러(알림전송실패)가 나도 무시하고 넘어감.
			try {
				String memberId = workDTO.getMem_id();
				if(memberId != null) {
					workService.sendWorkAlarm(workDTO, memberId);
				}
			} catch (Exception e) {
				System.out.println("⚠ 알림 발송 실패 (업무는 정상 등록됨): " + e.getMessage());
			}
			
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
	
	@GetMapping("/detail/{works_idx}")
	public ResponseEntity<WorkDTO> getWorkDetail(@PathVariable("works_idx") Integer works_idx) {
		WorkDTO workDTO = workMapper.getWorkDetail(works_idx);
		
		if(workDTO == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.ok(workDTO);
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
	
	/* 업무 완료/미완료 변경 로직  */
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
	
	/* 업무 상세정보 수정 로직 */
	@PatchMapping("update/detail")
	public ResponseEntity<WorkDTO> updateWorkDetail(
			@RequestBody WorkDTO workDTO
			) {
		int result = workMapper.updateWorkDetail(workDTO);
		WorkDTO workDetail = workMapper.getWorkDetail(workDTO.getWorks_idx());
		if(result > 0) {
			return ResponseEntity.ok(workDetail);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	/* 업무 삭제 로직(실제로 삭제는 안돼고 works_del_flag -> 'Y') */
	@PatchMapping("/Delete/{works_idx}")
	@ResponseBody
	public ResponseEntity<WorkDTO> readDelete(
			@PathVariable("works_idx") Integer works_idx) {
		// TODO: 업무 삭제
		workMapper.deleteWork(works_idx);
		
		WorkDTO deleted = workMapper.getWorkDetail(works_idx);
		String works_del_flag = deleted.getWorks_del_flag();
		
		ResponseEntity<WorkDTO> response = 
				(works_del_flag.equals("Y"))
					? ResponseEntity.ok(deleted)
					: ResponseEntity.badRequest().body(null);
		
		return response;
	}
	
}