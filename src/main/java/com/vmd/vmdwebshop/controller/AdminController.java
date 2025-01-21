package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * Creates an admin in the database that takes the username and password as parameters
     * and returns the String "success" if completed.
     * @param username
     * @param password
     * @return String
     */
    @PostMapping("admin/create")
    public String code(@RequestParam String username, @RequestParam String password) {
        try {
            adminService.createAdmin(username, password);
            return "success";
        } catch (Exception e) {
            return "failed";
        }
    }
}
//password: 1stpassword"
