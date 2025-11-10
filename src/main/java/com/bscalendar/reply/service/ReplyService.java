package com.bscalendar.reply.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bscalendar.reply.dto.ReplyCreateDTO;
import com.bscalendar.reply.dto.ReplyResponseDTO;
import com.bscalendar.reply.dto.ReplyUpdateDTO;
import com.bscalendar.reply.mapper.ReplyMapper;   

@Service
public class ReplyService {

    @Autowired
    private ReplyMapper replyMapper; 

    @Transactional
	public ReplyResponseDTO createReply(ReplyCreateDTO createDto, String memId) {
	        
	        createDto.setMemId(memId); 
	        int result = replyMapper.insertReply(createDto); 
	
	        if (result == 0) {
	            throw new RuntimeException("댓글 생성에 실패했습니다.");
	        }
	        return replyMapper.getReplyByIdx(createDto.getReplyIdx());
	    }

    public List<ReplyResponseDTO> getRepliesByWorksIdx(int worksIdx) {
        return replyMapper.getRepliesByWorksIdx(worksIdx);
    }

    @Transactional
    public ReplyResponseDTO updateReply(ReplyUpdateDTO updateDto, String memId) {
        
        //DB에서 원본 작성자 ID만 조회
        String originalAuthorId = replyMapper.getAuthorMemIdByReplyIdx(updateDto.getReplyIdx());

        if (originalAuthorId == null) {
            throw new RuntimeException("수정할 댓글이 존재하지 않습니다.");
        }

        //로그인한 사용자와 댓글 작성자가 동일한지 확인
        if (!originalAuthorId.equals(memId)) {
            throw new RuntimeException("댓글을 수정할 권한이 없습니다.");
        }
        
        //댓글 내용 업데이트
        int result = replyMapper.updateReply(updateDto);

        if (result == 0) {
            throw new RuntimeException("댓글 수정 실패 (DB 오류).");
        }

        //수정 완료 후, 화면에 보여줄 응답용 DTO를 조회하여 반환
        return replyMapper.getReplyByIdx(updateDto.getReplyIdx());
    }

    @Transactional
    public void deleteReply(int replyIdx, String memId) {
        
        // DB에서 원본 작성자 ID만 조회
        String originalAuthorId = replyMapper.getAuthorMemIdByReplyIdx(replyIdx);

        if (originalAuthorId == null) {
            throw new RuntimeException("삭제할 댓글이 존재하지 않습니다.");
        }

        //로그인한 사용자와 댓글 작성자가 동일한지 확인
        if (!originalAuthorId.equals(memId)) {
            throw new RuntimeException("댓글을 삭제할 권한이 없습니다.");
        }

        //댓글 삭제
        int result = replyMapper.deleteReply(replyIdx);

        if (result == 0) {
            throw new RuntimeException("댓글 삭제 실패 (DB 오류).");
        }
    }

}