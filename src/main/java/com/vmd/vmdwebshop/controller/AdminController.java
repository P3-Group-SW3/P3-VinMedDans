package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.model.Admin;
import com.vmd.vmdwebshop.repository.AdminRepository;
import com.vmd.vmdwebshop.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/password/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * Creates an admin in the database
     * @param username
     * @param password
     * @return
     */
    @PostMapping("admin/create")
    public String code(@RequestParam String username, @RequestParam String password) {
        try {
            adminService.CreateAdmin(username,password);
            return "succes";
        }
        catch (Exception e){
            return "failed";
        }
    }
}
//password: 1stpassword"
