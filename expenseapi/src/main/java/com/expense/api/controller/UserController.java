package com.expense.api.controller;

import com.expense.api.entity.Userdata;
import com.expense.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    //private final UserService userService;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody Userdata user) {
        String response = userService.registerUser(user);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", response);

        if ("User already registered".equals(response)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody); //conflict
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody); // 201 Created
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Userdata user) {
        try {
//            String msg = userService.verify(email, password);
//            return ResponseEntity.ok(msg);
            String token = userService.verify(user);
            Map<String ,String> map = new HashMap<>();
            map.put("token", token);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }

    }
}
