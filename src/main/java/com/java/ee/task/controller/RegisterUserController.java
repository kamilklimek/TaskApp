package com.java.ee.task.controller;

import com.java.ee.task.organizer.UserFacade;
import com.java.ee.task.organizer.entity.User;
import com.java.ee.task.security.service.AutoLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
@Slf4j
@RequestMapping("/sign-up")
public class RegisterUserController {
    private final UserFacade userFacade;
    private final AutoLoginService autoLoginService;

    @Autowired
    public RegisterUserController(UserFacade userFacade, AutoLoginService autoLoginService) {
        this.userFacade = userFacade;
        this.autoLoginService = autoLoginService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String registerUserForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(User user, HttpServletRequest request, Model model) {
        try {
            final Long userId = userFacade.registerUser(user);

        } catch (Exception e) {
            log.error("Error while register user: {} because of: {}", user, e.getMessage());
        }

        // autoLoginService.autoLoginUser(user, request);

        return "redirect:/project";
    }
}
