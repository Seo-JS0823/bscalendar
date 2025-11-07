package com.bscalendar.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bscalendar.project.dto.ProjectDTO;

@Mapper
public interface ProjectMapper {
	
	public int projectCreate(ProjectDTO project);
}
