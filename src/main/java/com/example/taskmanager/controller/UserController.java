// src/main/java/com/example/taskmanager/controller/UserController.java
package com.example.taskmanager.controller;

import com.example.taskmanager.model.User;
import com.example.taskmanager.security.CustomUserDetails;
import com.example.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public User getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.findById(userDetails.getUser().getId()).orElse(null);
    }

    @PutMapping("/profile")
    public User updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestPart("user") User user, @RequestPart("avatar") MultipartFile avatar) {
        User currentUser = userService.findById(userDetails.getUser().getId()).orElse(null);
        if (currentUser != null) {
            currentUser.setUsername(user.getUsername());
            // Save avatar logic (omitted for brevity)
            return userService.saveUser(currentUser);
        }
        return null;
    }
}