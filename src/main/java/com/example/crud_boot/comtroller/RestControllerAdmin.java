package com.example.crud_boot.comtroller;

import com.example.crud_boot.model.User;
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
import java.util.List;

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
    public String updateUser(@RequestBody String request) throws SQLException {

        return ("OK");
    }

    @PostMapping(value = "/admin/newUser")
    public ResponseEntity<?> createNewUser(@RequestBody User user) throws SQLException, JSONException, JsonProcessingException {

       System.out.println("start new user");
        System.out.println(user.getUsername());
    /*    String newUserJsonString = request;
        JSONObject userJson = new JSONObject(newUserJsonString);
        User user = new User();
        user.setFirstName(userJson.getString("firstName"));
        user.setLastName(userJson.getString("lastName"));
        user.setAge(userJson.getString("age"));
        user.setUsername(userJson.getString("username"));
        user.setPassword(userJson.getString("password"));
        JSONArray jsonRoles = userJson.getJSONArray("roles");
        int jsonRolesLength = jsonRoles.length();
        String role;
        for (int i = 0; i < jsonRolesLength; i++) {
            role = jsonRoles.getJSONObject(i).toString();
            user.getRoles().add(userService.getRoleByName(role));
        }
        userService.addUser(user);
        User userToJson = (User)userService.loadUserByUsername(user.getUsername());

        ObjectMapper mapper = new ObjectMapper();
        String JsonUserString = mapper.writeValueAsString(userToJson);
     */   return new ResponseEntity<>(user, HttpStatus.OK);
    }



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser(HttpServletRequest request, Model model) throws SQLException {
        String userForDeleteID = request.getParameter("del_userID");
        userService.deleteUser(userForDeleteID);
        return (userForDeleteID + " deleted");
    }

}
