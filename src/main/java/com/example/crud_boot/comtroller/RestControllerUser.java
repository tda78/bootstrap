package com.example.crud_boot.comtroller;

import com.example.crud_boot.model.User;
import com.example.crud_boot.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerUser {

    @Autowired
    private UserService userService;

    @GetMapping("/authority")
    public String getAuthority() throws JsonProcessingException {
        String string = (SecurityContextHolder.getContext().getAuthentication().
                getPrincipal().toString());
        User auth = (User) userService.loadUserByUsername(string);

        ObjectMapper mapper = new ObjectMapper();
        String JsonAuthor = mapper.writeValueAsString(auth);
        return (JsonAuthor);
    }
}