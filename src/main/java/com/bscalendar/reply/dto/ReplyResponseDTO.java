package com.bscalendar.reply.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDTO {

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
    
}