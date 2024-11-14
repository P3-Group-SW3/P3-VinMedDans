package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.Admin;
import com.vmd.vmdwebshop.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class); /* Keep for debugging */

    private final AdminRepository adminRepository;

    @Autowired
    public CustomUserDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     * Load admin by username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // logger.info("Loading admin by username: {}", username); /* Debugging */
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);
        Admin admin = adminOptional.orElseThrow(() -> {
            // logger.error("Admin not found: {}", username); //* Debugging */
            return new UsernameNotFoundException("Admin not found");
        });
        // logger.info("Admin found: {}", username); //* Debugging */
        return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), new ArrayList<>());
    }
}