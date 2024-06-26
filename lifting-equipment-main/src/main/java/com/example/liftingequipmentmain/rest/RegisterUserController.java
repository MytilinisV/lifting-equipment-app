package com.example.liftingequipmentmain.rest;

import com.example.liftingequipmentmain.dto.RegisterUserDTO;
import com.example.liftingequipmentmain.exceptions.UserAlreadyExistsException;
import com.example.liftingequipmentmain.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


//@RestController
@Controller
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class RegisterUserController {


    private final IUserService userService;

    @GetMapping("/users/register")
    public String login(Model model) {
        model.addAttribute("userForm", new RegisterUserDTO());
        return "register";
    }

    @PostMapping("/users/register")
    public String registration(@Valid @ModelAttribute("userForm") RegisterUserDTO userDTO, BindingResult bindingResult)
            throws UserAlreadyExistsException {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        return "redirect:/login";
    }
}
