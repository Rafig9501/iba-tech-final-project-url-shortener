package com.stepproject.ibatechurlshortener.controller;

import com.stepproject.ibatechurlshortener.dto.UserDto;
import com.stepproject.ibatechurlshortener.model.User;
import com.stepproject.ibatechurlshortener.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("registration")
    public String register() {
        System.out.println("@GetMapping(\"registration\") public String register()");
        return "html/registration";
    }

    @PostMapping("registration")
    public String takingParams(@RequestParam String name,
                               @RequestParam String lastName,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        System.out.println("public String takingParams + @PostMapping(\"registration\")\n");
        UserDto user = new UserDto(name, lastName, email, password);
        ResponseEntity<Optional<User>> responseEntity = userService.findByEmail(email);
        if (!responseEntity.getStatusCode().equals(HttpStatus.FOUND)) {
            model.addAttribute("info", "You have been registered successfully");
            userService.save(user);
            return "html/info"; // TODO implement fix register_success.html
        } else if (responseEntity.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            System.out.println("something went wrong"); //TODO handle exception
        }
        model.addAttribute("info", "something went wrong");
        return "html/info"; // TODO implement fix register_fail.html
    }
}
