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
        
        createDto.setMem_id(memId);
        int result = replyMapper.insertReply(createDto);

        if (result == 0) {
            throw new RuntimeException("댓글 생성에 실패했습니다.");
        }
        return replyMapper.getReplyByIdx(createDto.getReply_idx());
    }

    public List<ReplyResponseDTO> getRepliesByWorksIdx(int works_idx) {
        return replyMapper.getRepliesByWorksIdx(works_idx);
    }

    @Transactional
    public ReplyResponseDTO updateReply(ReplyUpdateDTO updateDto, String memId) {
        
        String originalAuthorId = replyMapper.getAuthorMemIdByReplyIdx(updateDto.getReply_idx());

        if (originalAuthorId == null) {
            throw new RuntimeException("수정할 댓글이 존재하지 않습니다.");
        }

        if (!originalAuthorId.equals(memId)) {
            throw new RuntimeException("댓글을 수정할 권한이 없습니다.");
        }
        
        int result = replyMapper.updateReply(updateDto);

        if (result == 0) {
            throw new RuntimeException("댓글 수정 실패");
        }

        return replyMapper.getReplyByIdx(updateDto.getReply_idx());
    }

    @Transactional
    public void deleteReply(int reply_idx, String memId) {
        
        String originalAuthorId = replyMapper.getAuthorMemIdByReplyIdx(reply_idx);

        if (originalAuthorId == null) {
            throw new RuntimeException("삭제할 댓글이 존재하지 않습니다.");
        }

        if (!originalAuthorId.equals(memId)) {
            throw new RuntimeException("댓글을 삭제할 권한이 없습니다.");
        }

        int result = replyMapper.deleteReply(reply_idx);

        if (result == 0) {
            throw new RuntimeException("댓글 삭제 실패");
        }
    }

}