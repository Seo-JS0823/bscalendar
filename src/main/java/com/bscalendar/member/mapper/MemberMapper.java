
package com.bscalendar.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {

	@Insert("INSERT INTO EG_MEMBER(mem_id, mem_pwd, mem_name, mem_position, mem_depart) VALUES(#{ mem_id }, #{ mem_pwd }, #{ mem_name }, #{ mem_position }, #{ mem_depart })")
	public int addMember(@Param("mem_id") String mem_id, @Param("mem_pwd") String mem_pwd, @Param("mem_name") String mem_name, @Param("mem_position") String mem_position, @Param("mem_depart") String mem_depart);

	@Select("SELECT COUNT(mem_id) FROM EG_MEMBER WHERE mem_id = #{ mem_id }")
	public int VariableId(@Param("mem_id") String mem_id);
	
	@Select("SELECT mem_idx, mem_id, mem_pwd, mem_name, mem_position, mem_depart, mem_del_flag"
			+ " FROM EG_MEMBER WHERE mem_id = #{ mem_id }")
	public HashMap<String,Object> getMember(@Param("mem_id") String mem_id);
		
	@Update("UPDATE EG_MEMBER"
			+ " SET mem_name = #{ mem_name }, mem_position = #{ mem_position }, mem_depart = #{ mem_depart }"
			+ " WHERE mem_id = #{ mem_id }")
	public int updateMember(@Param("mem_id") String mem_id, @Param("mem_name") String mem_name, @Param("mem_position") String mem_position, @Param("mem_depart") String mem_depart);
	
	@Update("UPDATE EG_MEMBER"
			+ " SET mem_name = #{ mem_name }, mem_position = #{ mem_position }, mem_depart = #{ mem_depart }, mem_pwd = #{ mem_pwd }"
			+ " WHERE mem_id = #{ mem_id }")
	public int updateMemberPwd(@Param("mem_id") String mem_id, @Param("mem_name") String mem_name, @Param("mem_position") String mem_position, @Param("mem_depart") String mem_depart, @Param("mem_pwd") String mem_pwd);
	
	@Update("UPDATE EG_MEMBER SET mem_del_flag  = 'Y' WHERE mem_id = #{ mem_id }")
	public int deleteMember(@Param("mem_id") String mem_id);

	@Select("SELECT COUNT(mem_id) FROM EG_MEMBER WHERE mem_id = #{ mem_id } AND mem_pwd = #{ mem_pwd }")
	public int loginMember(@Param("mem_id") String mem_id, @Param("mem_pwd")String mem_pwd);

	

	
	
}
