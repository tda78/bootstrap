package com.example.crud_boot.comtroller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class tempController {
    @GetMapping("/temp")
    public String function(Model model){
        return "hello";
    }
}
