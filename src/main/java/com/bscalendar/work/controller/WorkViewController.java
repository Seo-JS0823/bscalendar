package com.bscalendar.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bscalendar.work.dto.WorkDTO;
import com.bscalendar.work.mapper.WorkMapper;

@Controller
public class WorkViewController {
	
	@Autowired
	private WorkMapper workMapper;

	@GetMapping("/work/create/{mem_id}/{team_idx}")
	public String workCreate(
			@PathVariable("team_idx") Integer team_idx, 
			@PathVariable("mem_id") String mem_id,
			Model model
			) {
		model.addAttribute("team_idx", team_idx);
		model.addAttribute("mem_id", mem_id);
		return "work/work-create";
	}
	
	@GetMapping("/work/detail/{works_idx}")
	public String workRead(@PathVariable("works_idx") Integer works_idx, Model model) {
		WorkDTO workDTO = workMapper.getWorkDetail(works_idx);
		
		return "work/work-detail";
	}
	
}
