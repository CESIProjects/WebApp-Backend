package resources.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  
    @GetMapping("/home")
    public String handleWelcome() {
        return "home";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome() {
        return "Hello Admin!";
    }

    @GetMapping("/user/home")
    public String handleUserHome() {
        return "Hello User!";
    }
}
