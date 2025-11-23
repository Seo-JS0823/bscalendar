package com.bscalendar.reply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bscalendar.reply.dto.AlramDTO;
import com.bscalendar.reply.mapper.AlramMapper;

@Service
public class AlramService {

	@Autowired
	private AlramMapper alramMapper;
	
	// TODO: 알림 이벤트 발생 시 EG_ALRAM에 Insert
	// 정상 등록 true
	// 등록 실패 false
	public boolean alramInsert(AlramDTO alram) {
		int inserted = alramMapper.alramInsert(alram);
		return inserted > 0;
	}
	
	// TODO: 안 읽은 알람 조회
	public List<AlramDTO> alramReadAll(String mem_id) {
		List<AlramDTO> alramList = alramMapper.alramReadAll(mem_id);
		return alramList;
	}
	
	// TODO: 알람 읽으면 READ_FLAG UPDATE
	// 정상 업데이트 true
	// 업데이트 실패 false
	public boolean alramSeeUpdate(int alram_idx) {
		int updated = alramMapper.alramSeeUpdate(alram_idx);
		return updated > 0;
	}
	
}
