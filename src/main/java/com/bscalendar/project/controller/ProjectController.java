package com.bscalendar.project.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	// 2025-11-18 TODO: 달력의 sdate, edate resource로 추가해서 수정하기
	@GetMapping("/members/work/list/{member_id}/{team_idx}")
	@ResponseBody
	public ResponseEntity<List<MemberWorkDTO>> projectMemberWorkRead(
			@PathVariable("member_id") String member_id,
			@PathVariable("team_idx") Integer team_idx) {
		
		// TODO: team_idx와 member_id에 해당하는 work 리스트 가져오기
		List<MemberWorkDTO> memberToWorks = projectSvc.projectMemberWorkRead(member_id, team_idx);
		
		if(memberToWorks == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
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
	
	// project Member 검색
	@GetMapping("/member/read/{search}")
	@ResponseBody
	public ResponseEntity<List<ProjectMemberDTO>> projectMembersSearch(
			@PathVariable("search") String search) {
		System.out.println("SEARCH : " + search);
		// TODO: member name read service
		List<ProjectMemberDTO> searchToMembers = projectSvc.projectMembersSearch(search);
		
		if(searchToMembers == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		return ResponseEntity.ok(searchToMembers);
	}
	
	// project Member All Read
	@GetMapping("/member/read-all/{team_idx}")
	@ResponseBody
	public ResponseEntity<List<ProjectMemberDTO>> projectMembersAll(@PathVariable("team_idx") Integer team_idx) {
		// TODO: member all service
		List<ProjectMemberDTO> allMember = projectSvc.projectMemberAll(team_idx);
		
		if(allMember == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(allMember);
	}
	
	// 선택한 멤버를 body로 받아서 Member를 프로젝트 팀에 투입
	@PostMapping("/member/add/{team_idx}")
	@ResponseBody
	public ResponseEntity<Object> projectMemberAdd(
			@RequestBody Map<String, List<String>> mem_ids,
			@PathVariable("team_idx") Integer team_idx) {
		List<String> memberIds = mem_ids.get("members");
		
		// TODO: service insert
		Map<String, Object> inserted = projectSvc.projectMemberAdd(memberIds, team_idx);
		return ResponseEntity.ok(inserted);
	}
	
	// 프로젝트 설정을 위한 프로젝트 설정 데이터
	@GetMapping("/setting/{team_idx}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> projectSettingData(@PathVariable("team_idx") Integer team_idx) {
		// TODO: team_idx로 EG_TEAM 데이터 가져오기
		ProjectDTO targetProject = projectSvc.projectSettingRead(team_idx);
		if(targetProject == null) {
			System.out.println("왓아유 두잉 왓!? " + targetProject);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
				"message", "요청 에러로 인해 프로젝트 설정 정보를 찾을 수 없습니다. 다시 시도하여 주세요."
			));
		}
		return ResponseEntity.ok(Map.of(
			"project", targetProject
		));
	}
	
	@PatchMapping("/setting/{team_idx}/{team_edate}/{team_name}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> projectUpdate(
			@PathVariable("team_idx") Integer team_idx,
			@PathVariable("team_edate") String team_edate,
			@PathVariable("team_name") String team_name) {
		// TODO: 프로젝트 수정
		boolean projectUpdated = projectSvc.projectSettingUpdate(team_idx, team_edate, team_name);
		
		// TODO: true  : 업데이트 O
		// TODO: false : 업데이트 X
		if(!projectUpdated) {
			return ResponseEntity.ok(Map.of(
				"message", "예기치 않은 오류로 인해 설정 변경에 실패하였습니다. 다시 시도해주세요."
			));
		}
		return ResponseEntity.ok(Map.of(
			"message", "프로젝트 설정이 정상적으로 변경되었습니다."
		));
	}
	
	@DeleteMapping("/endProject/{team_idx}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> projectDelete(
			@PathVariable("team_idx") Integer team_idx) {
		// TODO: 프로젝트 삭제이지만 TEAM_DEL_FLAG 만 Y로 변경
		boolean projectDeleted = projectSvc.projectDelete(team_idx);
		if(!projectDeleted) {
			return ResponseEntity.ok(Map.of(
				"message", "예기치 않은 오류로 인해 프로젝트를 종료시키지 못하였습니다. 다시 시도해주세요."
			));
		}
		return ResponseEntity.ok(Map.of(
			"message", "프로젝트가 정상적으로 종료되었습니다."
		));
	}
	
	@DeleteMapping("/member/del/{team_idx}/{mem_id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> projectMemberDelete(
			@PathVariable("team_idx") Integer team_idx,
			@PathVariable("mem_id") String mem_id) {
		// TODO: 프로젝트에서 멤버 추방이지만 MAPP_DEL_FLAG만 Y로 변경
		String projectMemberDeleted = projectSvc.projectMemberDelete(team_idx, mem_id);
		if(projectMemberDeleted == null) {
			return ResponseEntity.badRequest().body(null);
		}
		
		return ResponseEntity.ok(Map.of(
			"message", projectMemberDeleted + " 님이 정상적으로 프로젝트에서 제외 되었습니다."
		));
	}
	
	@GetMapping("/work/hide/{team_idx}/{mem_id}")
	@ResponseBody
	public ResponseEntity<List<MemberWorkDTO>> workHideData(
			@PathVariable("team_idx") Integer team_idx,
			@PathVariable("mem_id") String mem_id) {
		// TODO: team_idx , mem_id 로 비공유 업무 렌더링할 데이터 가져오기
		List<MemberWorkDTO> hideWorks = projectSvc.getHideWorks(team_idx, mem_id);
		if(hideWorks.size() == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(hideWorks);
	}
	
	@GetMapping("/work/cal/{calendarSdate}/{calendarEdate}/{team_idx}/{mem_id}")
	@ResponseBody
	public ResponseEntity<List<MemberWorkDTO>> calendarRenderingData(
			@PathVariable("calendarSdate") String sdate,
			@PathVariable("calendarEdate") String edate,
			@PathVariable("team_idx") Integer team_idx,
			@PathVariable("mem_id") String mem_id) {
		// TODO: 캘린더에 띄울 막대기를 위한 데이터를 조회
		// TODO: 캘린더에 띄울 막대기 데이터는 날짜 비교 필요 X
		
		/*
		// TODO: 공유 업무 리스트
		List<MemberWorkDTO> noHideWorks = projectSvc.calendarNoHideWorks(date, team_idx);
		
		// TODO: 개인 업무 리스트
		List<MemberWorkDTO> hideWorks = projectSvc.calendarHideWorks(date, team_idx, mem_id);
		
		// TODO: List 병합
		List<MemberWorkDTO> response = new ArrayList<>();
		Collections.addAll(response, noHideWorks.toArray(new MemberWorkDTO[0]));
		Collections.addAll(response, hideWorks.toArray(new MemberWorkDTO[0]));
		*/
		
		// TODO: Service에서 병합된 리스트 가져오기
		List<MemberWorkDTO> response = projectSvc.calendarToDateWorks(sdate, edate, team_idx, mem_id);
		
		// TODO: 사이즈 검사
		if(response.size() == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		// TODO: return
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/work/cal/{nowDate}/{team_idx}/{mem_id}")
	@ResponseBody
	public ResponseEntity<List<MemberWorkDTO>> workListRenderingData(
			@PathVariable("nowDate") String nowDate,
			@PathVariable("team_idx") Integer team_idx,
			@PathVariable("mem_id") String mem_id) {
		// TODO: 캘린더 오른쪽에 띄울 업무 데이터 조회
		
		// TODO: Service에서 병합된 리스트 가져오기
		List<MemberWorkDTO> response = projectSvc.worksListToDate(nowDate, team_idx, mem_id);
		
		// TODO: 사이즈 검사
		if(response.size() == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.ok(response);
	}
}
