package com.example.hadistore.controller;

import com.example.hadistore.entity.User;
import com.example.hadistore.repository.UserRepository;
import com.example.hadistore.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("${api.prefix}/forgot-password")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResetPasswordController {
    UserService userService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @GetMapping("{email}")
    public String resetPassword(Model map, @PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return "redirect:/api/v1/forgot-password/error";
        }
        map.addAttribute("name", user.getName());
        map.addAttribute("email", user.getEmail());
        map.addAttribute("password", "");
        map.addAttribute("confirm", "");
        return "/reset-password";
    }

    @PostMapping
    public String reset(@RequestParam("password") String password, @RequestParam("confirm") String confirm,
                        @RequestParam("email") String email, @RequestParam("name") String name,
                        Model map) {
        if (password.length() < 6) {
            map.addAttribute("password", password);
            map.addAttribute("confirm", confirm);
            map.addAttribute("name", name);
            map.addAttribute("email", email);
            map.addAttribute("errorPassword", "error");
            return "/reset-password";
        }
        if (!password.equals(confirm)) {
            map.addAttribute("name", name);
            map.addAttribute("email", email);
            map.addAttribute("password", password);
            map.addAttribute("confirm", confirm);
            map.addAttribute("errorConfirm", "error");
            return "/reset-password";
        }
        User user = userService.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setStatus(true);
        userRepository.save(user);
        return "redirect:/api/v1/forgot-password/done";
    }

    @GetMapping("/done")
    public String done() {
        return "/done";
    }

    @GetMapping("/error")
    public String error() {
        return "/error";
    }

}