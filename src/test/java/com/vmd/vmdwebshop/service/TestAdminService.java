package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.admin.AdminNotSaved;
import com.vmd.vmdwebshop.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.vmd.vmdwebshop.model.Admin;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestAdminService {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    List<Admin> adminList = new ArrayList<>() {};

    Admin admin = null;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test

        admin = mock(Admin.class);
    }

    @Test
    public void TestCreateAdmin01() {
        assertThrows(AdminNotSaved.class, ()-> {
            adminService.createAdmin("Mogens","1stpassword");
        });
    }
}
