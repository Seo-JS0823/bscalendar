package com.bscalendar.reply.dto;

<<<<<<< Updated upstream
import com.fasterxml.jackson.annotation.JsonProperty;

=======
>>>>>>> Stashed changes
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< Updated upstream

=======
/**
 * 서버가 프론트엔드로 '응답'할 때 보내는 데이터 (Response)
 * (DB의 EG_REPLY 테이블과 EG_MEMBER를 JOIN한 결과)
 */
>>>>>>> Stashed changes
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDTO {

<<<<<<< Updated upstream
    @JsonProperty("reply_idx") 
    private int reply_idx; 
    
    @JsonProperty("works_idx")
    private int works_idx;
    
    @JsonProperty("mem_id")    
    private String mem_id;
    
    @JsonProperty("mem_name") 
    private String mem_name;
    
    @JsonProperty("reply_comment") 
    private String reply_comment; 
    
    @JsonProperty("reply_regdate") 
    private String reply_regdate;
=======
    private int replyIdx; //댓글 고유아이디
    
    private int worksIdx; //댓글이 적힌 업무
    
    private String memId; //작성자아이디
    
    private String memName; //작성자
    
    private String replyComment; //내용
    
    private String replyRegdate; //작성일
>>>>>>> Stashed changes
    
}