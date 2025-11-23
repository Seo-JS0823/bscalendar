package com.bscalendar.reply.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bscalendar.reply.dto.AlramDTO;
import com.bscalendar.reply.service.AlramService;

@Controller
@RequestMapping("/api/alram")
public class AlramController {

	@Autowired
	private AlramService alramService;
	
	@GetMapping("/{mem_id}")
	@ResponseBody
	public ResponseEntity<List<AlramDTO>> alramList(
			@PathVariable("mem_id") String mem_id) {
		List<AlramDTO> alramNoReadList = alramService.alramReadAll(mem_id);
		int size = alramNoReadList.size();
		
		if(size == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.ok(alramNoReadList);
	}
}
