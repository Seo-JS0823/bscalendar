package com.bscalendar.work.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bscalendar.work.dto.WorkDTO;

@Mapper
public interface WorkMapper {

	/* 업무 완료/미완료 변경 로직 */
	int workUpdate(Integer works_idx, String works_fin_flag);

	/* 멱등성 방어 */
	WorkDTO findWorkToIdx(Integer works_idx);

	/* 업무 등록 로직 */
	int workCreate(WorkDTO workDTO);
	
	/* 업무 상세정보 SELECT 로직 */
	WorkDTO getWorkDetail(Integer works_idx);
	
	/* 날짜기준으로 업무 SELECT 로직 */
	List<WorkDTO> findToDateWorks(String date, Integer team_idx);
	
	/* 업무 수정 로직 */
	int updateWorkDetail(WorkDTO workDTO);

	/* 업무 삭제 로직(WORKS_DEL_FLAG를 'Y'로 변경) */
	int deleteWork(Integer works_idx);
	
	//알람 기능 위한 추가
	List<WorkDTO> getTeamMemberIds(int team_idx);
}
