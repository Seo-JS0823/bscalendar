package com.bscalendar.reply.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class TestPageController {

    @GetMapping("/test/reply")
    public String showReplyTestPage(HttpSession session, Model model) {

        session.setAttribute("loginMemberId", "tengen");
        
        int testWorksIdx = 1; 
        model.addAttribute("testWorksIdx", testWorksIdx);

        return "reply/test";
    }
}