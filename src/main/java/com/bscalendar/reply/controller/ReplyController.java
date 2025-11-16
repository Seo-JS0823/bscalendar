package com.bscalendar.reply.controller;

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

import com.bscalendar.reply.dto.ReplyCreateDTO;
import com.bscalendar.reply.dto.ReplyUpdateDTO;
import com.bscalendar.reply.dto.ReplyResponseDTO;
import com.bscalendar.reply.service.ReplyService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/reply")
public class ReplyController {
	
	@Autowired
<<<<<<< Updated upstream
	private ReplyService replyService;
	
	/**
	 * 1. 댓글 생성 (POST)
	 * (URL: POST /api/reply)
	 */
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<ReplyResponseDTO> replyCreate(
			@RequestBody ReplyCreateDTO createDto,
			HttpSession session) {
		String loginMemberId = (String) session.getAttribute("loginMemberId");

		if (loginMemberId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
=======
	private ReplyService replyService; 
	
	
	@PostMapping("/")
	@ResponseBody
	public ResponseEntity<ReplyResponseDTO> replyCreate(
			@RequestBody ReplyCreateDTO createDto,
			HttpSession session) { 
		String loginMemberId = (String) session.getAttribute("loginMemberId");

		// 비로그인 사용자 차단
		if (loginMemberId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
		}
		
		//서비스 호출 시 DTO와 함께 로그인 ID도 전달
>>>>>>> Stashed changes
		ReplyResponseDTO response = replyService.createReply(createDto, loginMemberId);
		return ResponseEntity.ok(response);		
	}
	
<<<<<<< Updated upstream
	/**
	 * 2. 댓글 목록 조회 (GET)
	 * (URL: GET /api/reply)
	 */
	@GetMapping("/list")
	@ResponseBody
	public ResponseEntity<List<ReplyResponseDTO>> replyRead(
			@RequestParam("works_idx") int works_idx
	) {		
		
		List<ReplyResponseDTO> replyList = replyService.getRepliesByWorksIdx(works_idx);
		return ResponseEntity.ok(replyList);		
	}
	
	/**
	 * 3. 댓글 수정 (PUT)
	 * (URL: PUT /api/reply/1)
	 */
	@PutMapping("/{reply_idx}")
	@ResponseBody
	public ResponseEntity<ReplyResponseDTO> replyUpdate(
			@PathVariable("reply_idx") int reply_idx,
			@RequestBody ReplyUpdateDTO updateDto,
			HttpSession session) {
		
		String loginMemberId = (String) session.getAttribute("loginMemberId");
		
=======
	@GetMapping("/") 
	@ResponseBody
	public ResponseEntity<List<ReplyResponseDTO>> replyRead(@RequestParam("worksIdx") int worksIdx) {		
		List<ReplyResponseDTO> replyList = replyService.getRepliesByWorksIdx(worksIdx);
		return ResponseEntity.ok(replyList);		
	}
	
	@PutMapping("/{replyIdx}")
	@ResponseBody
	public ResponseEntity<ReplyResponseDTO> replyUpdate(
			@PathVariable("replyIdx") int replyIdx, 
			@RequestBody ReplyUpdateDTO updateDto,
			HttpSession session) {
		//세션에서 로그인한 사용자 ID 가져오기
		String loginMemberId = (String) session.getAttribute("loginMemberId");
		
		//비로그인 사용자 차단
>>>>>>> Stashed changes
		if (loginMemberId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
		}
		
<<<<<<< Updated upstream
		updateDto.setReply_idx(reply_idx);
		
=======
		updateDto.setReplyIdx(replyIdx);
		
		//서비스 호출 시 DTO와 로그인 ID 전달
>>>>>>> Stashed changes
		try {
			ReplyResponseDTO response = replyService.updateReply(updateDto, loginMemberId);
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
<<<<<<< Updated upstream
=======
			// 서비스에서 발생한 권한 없음 예외 처리
>>>>>>> Stashed changes
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
		}
	}
	
<<<<<<< Updated upstream
	/**
	 * 4. 댓글 삭제 (DELETE)
	 * (URL: DELETE /api/reply/1)
	 */
	@DeleteMapping("/{reply_idx}")
	@ResponseBody
	public ResponseEntity<Void> replyDelete(
			@PathVariable("reply_idx") int reply_idx,
			HttpSession session) {
		
		String loginMemberId = (String) session.getAttribute("loginMemberId");

		if (loginMemberId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
		}
		
		try {
			replyService.deleteReply(reply_idx, loginMemberId);
			return ResponseEntity.ok().build(); // 삭제 성공
=======
	@DeleteMapping("/{replyIdx}")
	@ResponseBody
	public ResponseEntity<Void> replyDelete(
			@PathVariable("replyIdx") int replyIdx,
			HttpSession session) {
		//세션에서 로그인한 사용자 ID 가져오기
		String loginMemberId = (String) session.getAttribute("loginMemberId");

		//비로그인 사용자 차단
		if (loginMemberId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
		}
		
		//서비스 호출 시 ID와 로그인 ID 전달
		try {
			replyService.deleteReply(replyIdx, loginMemberId);
			return ResponseEntity.ok().build(); // 삭제 성공 (200 OK)
>>>>>>> Stashed changes
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
		}
	}
	
}