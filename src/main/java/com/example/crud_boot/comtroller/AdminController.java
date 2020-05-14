package com.example.crud_boot.comtroller;

import com.example.crud_boot.model.User;
import com.example.crud_boot.model.UserRole;
import com.example.crud_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listUsers(Model model) {
        List<User> users = new ArrayList();
        userService.getAllUsers().forEach(users::add);
        model.addAttribute("users", users);

        String string = (SecurityContextHolder.getContext().getAuthentication().
                getPrincipal().toString());
        User auth = (User) userService.loadUserByUsername(string);
        model.addAttribute("auth", auth);
        return "users";
    }

    @PostMapping("/update")
    public String updateUser(HttpServletRequest request, Model model) throws SQLException {
        String name = request.getParameter("edit_name");
        String password = request.getParameter("edit_password");
        String id = request.getParameter("edit_userID");
        User user = new User(name, password);
        user.setFirstName(request.getParameter("edit_firstName"));
        user.setLastName(request.getParameter("edit_lastName"));
        user.setAge(request.getParameter("edit_age"));

        List<String> roles = Arrays.asList(request.getParameterValues("edit_newUserRoles"));
        if (roles.contains("adminRole")) {
            user.getRoles().add(userService.getRoleByName("ADMIN"));
        } else {
            user.getRoles().remove(userService.getRoleByName("ADMIN"));
        }
        if (roles.contains("userRole")) {
            user.getRoles().add(userService.getRoleByName("USER"));
        } else {
            user.getRoles().remove(userService.getRoleByName("USER"));
        }


        user.setUser_id(Long.parseLong(id));
        userService.updateUser(user);
        String string = (SecurityContextHolder.getContext().getAuthentication().
                getPrincipal().toString());
        User auth = (User) userService.loadUserByUsername(string);
        model.addAttribute("auth", auth);

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "redirect:/admin/";
    }

    @PostMapping("/newUser")
    public String createNewUser(HttpServletRequest request, Model model) throws SQLException {

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String id = request.getParameter("userID");
        User user = new User(name, password);
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setAge(request.getParameter("age"));

        List<String> roles = Arrays.asList(request.getParameterValues("newUserRoles"));
        if (roles.contains("adminRole")) {
            user.getRoles().add(userService.getRoleByName("ADMIN"));
        } else {
            user.getRoles().remove(userService.getRoleByName("ADMIN"));
        }
        if (roles.contains("userRole")) {
            user.getRoles().add(userService.getRoleByName("USER"));
        } else {
            user.getRoles().remove(userService.getRoleByName("USER"));
        }
        userService.addUser(user);

        String string = (SecurityContextHolder.getContext().getAuthentication().
                getPrincipal().toString());
        User auth = (User) userService.loadUserByUsername(string);
        model.addAttribute("auth", auth);

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "redirect:/admin/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser(HttpServletRequest request, Model model) throws SQLException {
        userService.deleteUser(request.getParameter("del_userID"));
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        String string = (SecurityContextHolder.getContext().getAuthentication().
                getPrincipal().toString());
        User auth = (User) userService.loadUserByUsername(string);
        model.addAttribute("auth", auth);
        return "redirect:/admin/";
    }

}
