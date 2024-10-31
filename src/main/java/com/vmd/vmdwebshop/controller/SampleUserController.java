//package com.vmd.vmdwebshop.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/base/") // Base URL for user-related endpoints
//public class SampleUserController {
//
//    @Autowired
//    private SampleUserService userService;
//
//    @GetMapping("/users")
//    public ResponseEntity<List<SampleUser>> getAllUsers() {
//        List<SampleUser> users = userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
//
//    @PostMapping
//    public ResponseEntity<SampleUser> createUser(@RequestBody SampleUser user) {
//        SampleUser createdUser = userService.createUser(user);
//        return ResponseEntity.ok(createdUser);
//    }
//
//    // Additional endpoints for updating and deleting users can be added
//}
