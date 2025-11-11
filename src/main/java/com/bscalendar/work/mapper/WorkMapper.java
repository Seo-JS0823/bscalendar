package com.bscalendar.work.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bscalendar.work.dto.WorkDTO;

@Mapper
public interface WorkMapper {

	int workCreate(WorkDTO workDTO);

}
