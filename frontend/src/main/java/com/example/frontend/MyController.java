package com.example.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MyController {

//    @GetMapping("/")
//    public String home() {
//        return "Hello, World!";
//    }

    @GetMapping("/main")
    public String mainPage(Model model){
        model.addAttribute("message", "Witaj na stronie głównej!");
        return "main";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }


}