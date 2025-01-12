package org.example.Web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class LogController {

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        // Здесь можно добавить вашу логику проверки пользователя
        if ("admin".equals(username) && "password123".equals(password)) {
            model.addAttribute("username", username);
            return "loginResult"; // Имя представления (loginResult.html)
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Возврат на страницу логина
        }
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String email,
                                 Model model) {
        // Логируем регистрацию
        System.out.println("User Registration Attempt: " + username);

        // Добавляем информацию в модель
        model.addAttribute("message", "Registration attempt logged for " + username);

        // Возвращаем страницу
        return "registerResult";
    }
}