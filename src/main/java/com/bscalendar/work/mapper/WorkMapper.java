package com.bscalendar.work.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkMapper {
	
	int workUpdate(Integer works_idx);
}
