package com.bscalendar.work.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bscalendar.fcm.service.FcmPushService;
import com.bscalendar.work.dto.WorkDTO;
import com.bscalendar.work.mapper.WorkMapper;

@Service
public class WorkService {

    @Autowired(required = false)
    private FcmPushService fcmPushService;
    
    @Autowired
    private WorkMapper workMapper;

    public void sendWorkAlarm(WorkDTO workDTO, String loginMemberId) {
        // 1. FCM 서비스가 없으면 중단
        if (fcmPushService == null) return;

        // 비공유('Y') 업무라면 알림 안 보내고 종료
        if ("Y".equals(workDTO.getWorks_hide())) {
            System.out.println("비공유 업무이므로 알림을 발송하지 않습니다.");
            return; 
        }

        try {
            // 팀원 목록 가져오기
            // 아이디 조회
            List<String> teamMembers = workMapper.getTeamMemberIds(workDTO.getTeam_idx());
            
            if (teamMembers != null) {
                
                // 1. DTO에서 업무 내용(comment)을 가져옵니다.
                String content = workDTO.getWorks_comment();
                
                // 내용이 없을 경우를 대비해 null 체크, 20자로 글자 제한
                if (content != null && content.length() > 20) {
                    content = content.substring(0, 20) + "...";
                } else if (content == null) {
                    content = "새로운 업무"; // 내용이 아예 없을 때 기본 멘트
                }

                String title = "새로운 팀 업무 등록 📅";
                String body = loginMemberId + "님이 업무를 등록했습니다: " + content;

                for (String memberId : teamMembers) {
                    // 나 자신(!= loginMemberId)을 제외하고 팀원들에게 전송
                    if (!memberId.equals(loginMemberId)) { 
                         fcmPushService.sendNotificationToUser(memberId, title, body);
                         //!memberId의 !제거 시 알림 오는거 확인 가능
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("알림 전송 중 에러 발생 (무시됨): " + e.getMessage());
        }
    }
}