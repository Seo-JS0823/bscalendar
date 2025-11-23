package com.bscalendar.reply.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bscalendar.reply.dto.AlramDTO;

@Mapper
public interface AlramMapper {

	// TODO: 알림 이벤트 발생 시 EG_ALRAM에 Insert
	int alramInsert(AlramDTO alram);
	
	// TODO: nav.jsp에 안 읽은 알람 존재 시 Select
	List<AlramDTO> alramReadAll(String mem_id);
	
	// TODO: 알림을 클릭해서 읽었으면 read_flag -> Y로 업데이트
	int alramSeeUpdate(int alram_idx);
}
