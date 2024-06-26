package com.example.liftingequipmentmain.rest;

import com.example.liftingequipmentmain.model.User;
import com.example.liftingequipmentmain.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/users/dashboard")
    public String dashboard(Model model) throws Exception {
        List<User> users;
        try {
            users = userService.findAllUsers();
            model.addAttribute("users", users);
        } catch (Exception e) {
            throw e;
        }
        return "/users/dashboard";
    }
}
