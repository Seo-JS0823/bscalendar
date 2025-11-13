package com.bscalendar.work.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WorkViewController {

	@GetMapping("/work/create/{team_idx}")
	public String workCreate(@PathVariable("team_idx") Integer team_idx, Model model) {
		model.addAttribute("team_idx", team_idx);
		return "work/work-create";
	}
	
	@GetMapping("/work/detail/{works_idx}")
	public String workRead(@PathVariable("works_idx") Integer works_idx, Model model) {
		
		return "work/work-detail";
	}
	
}
