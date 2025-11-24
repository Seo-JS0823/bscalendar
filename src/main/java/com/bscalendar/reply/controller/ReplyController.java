package com.bscalendar.reply.controller;

import java.util.Date;
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
import com.bscalendar.reply.dto.AlramDTO;
import com.bscalendar.reply.dto.ReplyCreateDTO;
import com.bscalendar.reply.dto.ReplyResponseDTO;
import com.bscalendar.reply.dto.ReplyUpdateDTO;
import com.bscalendar.reply.service.AlramService;
import com.bscalendar.reply.service.ReplyService;

@Controller
@RequestMapping("/api/reply")
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;

	@Autowired
	private FcmPushService fcmPushService;
	
	@Autowired
	private AlramService alramService;
	
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); 
       }
		
        // 알림 발송 로직 추가
		try {
            String taskAuthorId = replyService.getTaskAuthorId(createDto.getWorks_idx());
            String taskAuthorName = response.getMem_name();
            // (!taskAuthorId 앞의 !을 지우면 나에게 알림이 옴)
            if (taskAuthorId != null && !taskAuthorId.equals(loginMemberId)) {
                	
            	String title = "새 댓글 알림 💬";
            	String body = loginMemberId + "님이 회원님의 업무에 새 댓글을 남겼습니다.";
            	
            	// 1. FCM 발송 (위에서 만든 변수 사용)
                fcmPushService.sendNotificationToUser(taskAuthorId, taskAuthorName,title, body);
                
                // 2. DB 저장
                try {
                	AlramDTO alram = new AlramDTO();
                	alram.setMem_id(taskAuthorId);      	
                	alram.setTitle(title);
                	alram.setMessage(body);                	
                	alram.setRead_flag("N");      
                	alram.setAlram_date(new Date());
                	
                	// 팀원이 만든 서비스 호출해서 DB에 저장
                	alramService.alramInsert(alram);
                	
                } catch (Exception dbE) {
                	System.out.println("DB 알림 저장 실패(무시): " + dbE.getMessage());
                }
            }
            
        } catch (Exception e) {	
        	e.printStackTrace();	
            System.out.println("댓글 알림 발송 실패 (무시): " + e.getMessage());
        }       
		return ResponseEntity.ok(response);		
	}
	
    //댓글목록조회
	@GetMapping("/list")
	@ResponseBody
	public ResponseEntity<List<ReplyResponseDTO>> replyRead(@RequestParam("works_idx") int works_idx) {		
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
		updateDto.setReply_idx(reply_idx);
		
		try {
			ReplyResponseDTO response = replyService.updateReply(updateDto, loginMemberId);
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); 
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
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
}