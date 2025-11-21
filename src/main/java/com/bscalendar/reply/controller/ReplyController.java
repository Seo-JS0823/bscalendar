package com.bscalendar.reply.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

import com.bscalendar.fcm.service.FcmPushService;
import com.bscalendar.jwt.SecurityUser;
import com.bscalendar.reply.dto.ReplyCreateDTO;
import com.bscalendar.reply.dto.ReplyResponseDTO;
import com.bscalendar.reply.dto.ReplyUpdateDTO;
import com.bscalendar.reply.service.ReplyService;

@Controller
@RequestMapping("/api/reply")
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;

	@Autowired
	private FcmPushService fcmPushService;
	
	//댓글등록조회
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<ReplyResponseDTO> replyCreate(
			@RequestBody ReplyCreateDTO createDto,
			Authentication authentication) {
		
		Object target = authentication.getPrincipal();
		String loginMemberId = "";
		if(target instanceof UserDetails) {
			loginMemberId = ((SecurityUser) target).getUsername();
			System.out.println("멤버 아디 : " + loginMemberId);
		}
		
		ReplyResponseDTO response = replyService.createReply(createDto, loginMemberId);
		
		if (response == null) {
            // 이 오류는 ReplyService가 RuntimeException을 던지지 않고 null을 반환했을 때 발생
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); 
       }
		
        // 알림 발송 로직 추가
        try {
            // ▼▼▼▼▼ [ SQL 조회 코드를 우회하고 현재 로그인 ID를 강제 사용 ] ▼▼▼▼▼
            String taskAuthorId = loginMemberId; // 알림 발송 테스트를 위해 현재 로그인 ID를 대상 ID로 설정
            
            // (참고: 이전에 주석 처리했던 if 문은 테스트를 위해 제거/해제합니다.)
                
            //"업무 원작성자"에게 알림 발송 (나 자신에게 알림이 옴)
            fcmPushService.sendNotificationToUser(
                taskAuthorId, 
                "새 댓글 알림 💬", // 이모지 추가
                loginMemberId + "님이 회원님의 업무에 새 댓글을 남겼습니다."
            );
            
        } catch (Exception e) {
            // SQL 쿼리 실패 시 여기서 로그가 찍힙니다.
        	e.printStackTrace();	
            System.out.println("댓글 알림 발송 실패 (무시): " + e.getMessage());
        }       
		return ResponseEntity.ok(response);		
	}
	
    //댓글목록조회
	@GetMapping("/list")
	@ResponseBody
	public ResponseEntity<List<ReplyResponseDTO>> replyRead(
			@RequestParam("works_idx") int works_idx
	) {		
		List<ReplyResponseDTO> replyList = replyService.getRepliesByWorksIdx(works_idx);
		return ResponseEntity.ok(replyList);		
	}
	
    //댓글 수정
	@PutMapping("/{reply_idx}")
	@ResponseBody
	public ResponseEntity<ReplyResponseDTO> replyUpdate(
			@PathVariable("reply_idx") int reply_idx,
			@RequestBody ReplyUpdateDTO updateDto,
			Authentication authentication) {
		
		String loginMemberId = authentication.getName();
		System.out.println("타깃 아이디 : " + loginMemberId);
		updateDto.setReply_idx(reply_idx);
		
		try {
			ReplyResponseDTO response = replyService.updateReply(updateDto, loginMemberId);
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403
		}
	}
	
    //댓글 삭제
	@DeleteMapping("/{reply_idx}")
	@ResponseBody
	public ResponseEntity<Void> replyDelete(
			@PathVariable("reply_idx") int reply_idx,
			Authentication authentication) {
		
		String loginMemberId = authentication.getName();
		
		try {
			replyService.deleteReply(reply_idx, loginMemberId);
			return ResponseEntity.ok().build();
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403
		}
	}
	
}