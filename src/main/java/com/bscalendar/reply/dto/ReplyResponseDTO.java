package com.bscalendar.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 서버가 프론트엔드로 '응답'할 때 보내는 데이터 (Response)
 * (DB의 EG_REPLY 테이블과 EG_MEMBER를 JOIN한 결과)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDTO {

    private int reply_idx; //댓글 고유아이디
    
    private int works_idx; //댓글이 적힌 업무
    
    private String mem_id; //작성자아이디
    
    private String mem_name; //작성자
    
    private String reply_comment; //내용
    
    private String reply_regdate; //작성일
    
}