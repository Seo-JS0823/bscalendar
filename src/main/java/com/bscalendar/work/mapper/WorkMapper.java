package com.bscalendar.work.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bscalendar.work.dto.WorkDTO;

@Mapper
public interface WorkMapper {
	
	int workUpdate(Integer works_idx);
	
	WorkDTO findWorkToIdx(Integer works_idx);

	int workCreate(WorkDTO workDTO);

	WorkDTO getWorkDetail(Integer works_idx);
	
	List<WorkDTO> findToDateWorks(String date, Integer team_idx);
	
	//알람을 보내기 위한 팀원ID 조회
	List<String> getTeamMemberIds(int team_idx);
}
