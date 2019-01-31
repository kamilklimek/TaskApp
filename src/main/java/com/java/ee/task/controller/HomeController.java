package com.java.ee.task.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequestMapping("/")
@Controller
@Slf4j
public class HomeController {
    @RequestMapping(value = "")
    public String getHomeView(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("logged", false);
        } else {
            model.addAttribute("login", principal.getName());
            model.addAttribute("logged", true);
        }
        return "home";
    }
}
