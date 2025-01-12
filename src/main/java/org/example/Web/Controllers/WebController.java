package org.example.Web.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String home() {
        return "forward:/index.html";
    }
    @GetMapping("/user/login")
    public String user() {
        return "forward:/loginResult.html";
    }
}
