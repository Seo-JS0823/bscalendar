package com.bscalendar.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bscalendar.project.mapper.ProjectMapper;

@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
}
