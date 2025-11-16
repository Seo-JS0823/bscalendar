package com.bscalendar.reply.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.bscalendar.reply.dto.ReplyCreateDTO;
import com.bscalendar.reply.dto.ReplyResponseDTO;
import com.bscalendar.reply.dto.ReplyUpdateDTO;

@Mapper
public interface ReplyMapper {

    int insertReply(ReplyCreateDTO createDto);
    
    ReplyResponseDTO getReplyByIdx(int reply_idx);

    List<ReplyResponseDTO> getRepliesByWorksIdx(int works_idx);

    int updateReply(ReplyUpdateDTO updateDto);

    int deleteReply(int reply_idx);

    String getAuthorMemIdByReplyIdx(int reply_idx);
    
}