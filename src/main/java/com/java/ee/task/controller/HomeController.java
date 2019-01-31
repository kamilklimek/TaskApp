package com.java.ee.task.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/")
@Controller
@Slf4j
public class HomeController {
    @RequestMapping(value = "")
    public String getHomeView(Principal principal) {
        return "home";
    }
}
