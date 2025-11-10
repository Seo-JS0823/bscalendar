package com.bscalendar.reply.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.bscalendar.reply.dto.ReplyCreateDTO;
import com.bscalendar.reply.dto.ReplyResponseDTO;
import com.bscalendar.reply.dto.ReplyUpdateDTO;

@Mapper
public interface ReplyMapper {
    int insertReply(ReplyCreateDTO createDto);
    
    ReplyResponseDTO getReplyByIdx(int replyIdx);

    List<ReplyResponseDTO> getRepliesByWorksIdx(int worksIdx);

    int updateReply(ReplyUpdateDTO updateDto);

    int deleteReply(int replyIdx);

	String getAuthorMemIdByReplyIdx(int replyIdx);
    
}