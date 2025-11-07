package com.bscalendar.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bscalendar.project.dto.ProjectDTO;

@Mapper
public interface ProjectMapper {
	
	// 프로젝트 생성
	public int projectCreate(ProjectDTO project);

	// 프로젝트 조회(EG_MAPP 테이블 INSERT할 IDX)
	public int projectRead_idx(ProjectDTO project);
	
	// 프로젝트 생성
	public int projectCreate_mapp(ProjectDTO project);
	
	// 프로젝트 조회 (Value : mem_id)
	public List<ProjectDTO> projectRead(ProjectDTO project);
}
