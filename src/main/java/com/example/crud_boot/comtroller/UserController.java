package com.example.crud_boot.comtroller;

import com.example.crud_boot.model.User;
import com.example.crud_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/user/")
    public String userPage(Model model) {
        return "userInfo";
    }
}
