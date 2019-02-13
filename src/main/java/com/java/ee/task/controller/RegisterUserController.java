package com.java.ee.task.controller;

import com.java.ee.task.organizer.UserFacade;
import com.java.ee.task.organizer.entity.User;
import com.java.ee.task.organizer.exception.UserValidationException;
import com.java.ee.task.organizer.validation.UserValidationService;
import com.java.ee.task.security.service.AutoLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;


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

        } catch (UserValidationException e) {
            log.error("Caught user validation exception while trying to register user: {}", user);

            model.addAttribute("errors", e.getMessage());
            return "signup";

        }
        catch (Exception e) {
            log.error("Error while register user: {} because of: {}", user, e.getMessage());
            throw e;
        }


        return "redirect:/project";
    }
}
