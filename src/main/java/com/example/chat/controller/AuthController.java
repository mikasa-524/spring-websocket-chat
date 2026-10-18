package com.example.chat.controller;

import com.example.chat.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    // 简单登录示例：前端传 username（生产需校验密码）
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "username required"));
        }
        String token = JwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
