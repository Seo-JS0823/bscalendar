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
		
		ReplyResponseDTO response = replyService.createReply(createDto, loginMemberId);
		return ResponseEntity.ok(response);		
	}
	
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
		
		if (loginMemberId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
		}
		
		updateDto.setReply_idx(reply_idx);
		
		try {
			ReplyResponseDTO response = replyService.updateReply(updateDto, loginMemberId);
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
		}
	}
	
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
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
		}
	}
	
}