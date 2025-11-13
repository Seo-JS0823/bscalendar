package com.bscalendar.work.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bscalendar.work.dto.WorkDTO;

@Mapper
public interface WorkMapper {
	
	int workUpdate(Integer works_idx);
	
	WorkDTO findWorkToIdx(Integer works_idx);
}
