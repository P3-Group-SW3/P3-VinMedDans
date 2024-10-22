package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.SampleUser;
import com.vmd.vmdwebshop.repository.SampleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleUserService {

    @Autowired
    private SampleUserRepository userRepository;

    public List<SampleUser> getAllUsers() {
        return userRepository.findAll();
    }

    public SampleUser createUser(SampleUser user) {
        return userRepository.save(user);
    }

    // Additional methods can be added for updating/deleting users
}
