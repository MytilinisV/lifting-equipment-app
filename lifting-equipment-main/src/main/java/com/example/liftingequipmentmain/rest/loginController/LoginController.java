package com.example.liftingequipmentmain.rest.loginController;

import com.example.liftingequipmentmain.exceptions.EntityNotFoundException;
import com.example.liftingequipmentmain.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final IUserService userService;

    @GetMapping("/login")
    public String login(Model model, Principal principal, HttpServletRequest request) {

        return principal == null ? "redirect:/login" : "redirect:/dashboard";
    }

    @GetMapping(path = {"/"})
    String root(Model model, Principal principal, HttpServletRequest request) throws Exception {
//        return principal == null ? "login" : "redirect:/dashboard";
        return "redirect:/login";
    }
}
