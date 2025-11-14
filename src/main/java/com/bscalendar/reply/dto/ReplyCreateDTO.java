package com.bscalendar.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@NoArgsConstructor
@AllArgsConstructor 
public class ReplyCreateDTO {

    private int works_idx; // 어느 업무에 달린 댓글인지

    private String reply_comment; // 댓글 내용
    
    private String mem_id; //작성자 아이디
    
    private int reply_idx;

}