package com.bscalendar.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectViewController {
	
	@GetMapping("/project/list")
	public String projectListView() {
		
		return "project/project-list";
	}
	
	@GetMapping("/project/{team_idx}")
	public String projectDetailView(@PathVariable("team_idx") Integer team_idx, Model model) {
		System.out.println("TEAM_IDX: " + team_idx);
		model.addAttribute("team_idx", team_idx);
		
		return "project/project";
	}
	
}
