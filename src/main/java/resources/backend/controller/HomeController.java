package resources.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

    @GetMapping("/home")
    @ResponseBody
    public String handleWelcome() {
        return "home";
    }

    @GetMapping("/admin/home")
    @ResponseBody
    public String handleAdminHome() {
        return "Hello Admin!";
    }

    @GetMapping("/user/home")
    @ResponseBody
    public String handleUserHome() {
        return "Hello User!";
    }


}
