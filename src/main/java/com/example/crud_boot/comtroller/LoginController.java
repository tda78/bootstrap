package com.example.crud_boot.comtroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String logIn(Model model){
        return "login";
    }

    @GetMapping("/")
    public String startLogIn(Model model){
        return "login";
    }
}
