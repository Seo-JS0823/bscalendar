package com.bscalendar.reply.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bscalendar.reply.dto.ReplyDTO;

@Controller
@RequestMapping("/api/reply")
public class ReplyController {
	/* REST API URL
	 * 댓글 생성: POST,     /api/reply
	 * 댓글 조회: GET,      /api/reply
	 * 댓글 수정: PUT,      /api/reply
	 * 댓글 삭제: DELETE,   /api/reply
	 */
	
	@PostMapping("/")
	@ResponseBody
	public ResponseEntity<ReplyDTO> replyCreate() {
		// TODO: 댓글 생성
		
		return null;
	}
	
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<List<ReplyDTO>> replyRead() {
		// TODO: 댓글 조회
		
		return null;
	}
	
	@PutMapping("/")
	@ResponseBody
	public ResponseEntity<ReplyDTO> replyUpdate() {
		// TODO: 댓글 수정
		
		return null;
	}
	
	@DeleteMapping("/")
	@ResponseBody
	public ResponseEntity<ReplyDTO> replyDelete() {
		// TODO: 댓글 삭제
		
		return null;
	}
	
}
