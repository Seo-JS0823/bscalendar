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
		model.addAttribute("mem_id", "tengen");
		return "work/work-create";
	}
}
