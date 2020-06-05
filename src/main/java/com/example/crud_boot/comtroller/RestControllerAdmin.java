package com.example.crud_boot.comtroller;

import com.example.crud_boot.model.User;
import com.example.crud_boot.model.UserRole;
import com.example.crud_boot.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class RestControllerAdmin {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/all")
    public String getAll(Model model) throws JsonProcessingException {
        List<User> users = userService.getAllUsers();
        ObjectMapper mapper = new ObjectMapper();
        String JsonAllUsers = mapper.writeValueAsString(users);
        return (JsonAllUsers);

    }

    @PostMapping("/admin/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) throws SQLException {
        Set<UserRole> userRoles= new HashSet<>();
        for(Object roleString: user.getRoles()){
            userRoles.add(userService.getRoleByName(roleString.toString()));
        }
        user.setRoles(userRoles);
        userService.updateUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/admin/newUser")
    public ResponseEntity<?> createNewUser(@RequestBody User user) throws SQLException, JSONException, JsonProcessingException {

        Set<UserRole> userRoles= new HashSet<>();
        for(Object roleString: user.getRoles()){
            userRoles.add(userService.getRoleByName(roleString.toString()));
        }
        user.setRoles(userRoles);
        userService.addUser(user);
        User newUser = (User)userService.loadUserByUsername(user.getUsername());

        return new ResponseEntity<>(newUser.getUser_id(), HttpStatus.OK);
    }



    @PostMapping(value = "/admin/delete")
    public ResponseEntity<?> DeleteUser(@RequestBody User user) throws SQLException {

        userService.deleteUser(user.getUser_id());
        return (new ResponseEntity<>(HttpStatus.OK));
    }

}
