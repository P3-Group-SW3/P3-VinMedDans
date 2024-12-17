package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.admin.AdminNotSaved;
import com.vmd.vmdwebshop.model.Admin;
import com.vmd.vmdwebshop.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    /**
     * Adds an admin to the database
     * @param username
     * @param password
     */
    public void CreateAdmin(String username, String password){
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(bCryptPasswordEncoder.encode(password));
        adminRepository.save(admin);
        if(admin.getId() == null){
            throw new AdminNotSaved(username,password);
        }
    }
}
