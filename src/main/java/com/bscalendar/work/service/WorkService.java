package com.bscalendar.work.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bscalendar.fcm.service.FcmPushService;
import com.bscalendar.work.dto.WorkDTO;
import com.bscalendar.work.mapper.WorkMapper;

@Service
public class WorkService {

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private FcmPushService fcmPushService; // 알림 발송기 주입

    /**
     * 업무를 DB에 저장하고, 조건(공유)에 따라 팀원에게 알림을 발송합니다.
     */
    @Transactional
    public int insertWork(WorkDTO workDTO, String loginMemberId) {
        
        // 1. DB에 업무 저장
        int result = workMapper.workCreate(workDTO);

        // 2. 저장이 성공했고, '공유(N)' 업무라면 알림 발송
        if (result > 0 && "N".equals(workDTO.getWorks_hide())) {
            try {
                sendTeamNotification(workDTO, loginMemberId);
            } catch (Exception e) {
                // 알림 실패가 업무 등록 실패로 이어지지 않도록 로그만 남김
                System.out.println("업무 알림 발송 실패 (무시): " + e.getMessage());
            }
        }
        
        return result;
    }

    // 알림 발송 내부 메서드
    private void sendTeamNotification(WorkDTO workDTO, String loginMemberId) {
        // 팀원 목록 조회
        List<String> teamMembers = workMapper.getTeamMemberIds(workDTO.getTeam_idx());

        if (teamMembers != null) {
            for (String memberId : teamMembers) {
                // 나 자신에게는 보내지 않음
                if (!memberId.equals(loginMemberId)) {
                    
                    // 내용 한도 (20자)
                    String content = workDTO.getWorks_comment();
                    if (content != null && content.length() > 20) {
                        content = content.substring(0, 20) + "...";
                    }

                    // 알림 발송 (FcmPushService 호출)
                    fcmPushService.sendNotificationToUser(
                        memberId, 
                        "새 팀 업무 등록 📅", 
                        loginMemberId + "님이 업무를 등록했습니다: " + content
                    );
                }
            }
        }
    }
}