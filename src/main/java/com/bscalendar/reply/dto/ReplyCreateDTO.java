package com.bscalendar.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@NoArgsConstructor
@AllArgsConstructor 
public class ReplyCreateDTO {

    private int worksIdx; // 어느 업무에 달린 댓글인지

    private String replyComment; // 댓글 내용
    
    private String memId; //작성자 아이디
    
    private int replyIdx;

}