package com.bscalendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/test/weather")
	public String weather() {
		return "weather";
	}
}
