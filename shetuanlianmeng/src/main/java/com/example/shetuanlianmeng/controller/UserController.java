package com.example.shetuanlianmeng.controller;

import com.example.shetuanlianmeng.entity.User;
import com.example.shetuanlianmeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User authenticatedUser = userService.authenticate(user.getUsername(), user.getPassword());
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(401).body("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        boolean isRegistered = userService.register(user);
        if (isRegistered) {
            return ResponseEntity.ok("注册成功");
        } else {
            return ResponseEntity.status(400).body("注册失败");
        }
    }

    @PostMapping("/{userId}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long userId, @RequestBody Map<String, String> roleRequest) {
        String role = roleRequest.get("role");
        userService.updateUserRole(userId, role);
        return ResponseEntity.ok("角色更新成功");
    }
}
