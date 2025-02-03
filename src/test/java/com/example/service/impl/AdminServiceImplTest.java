package com.example.service.impl;

import com.example.entity.Admin;
import com.example.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Mockito annotations ko initialize karta hai
        admin = new Admin();
        admin.setId(1L);
        admin.setOwnerName("John Doe");
        admin.setOwnerContactNo("1234567890");
        admin.setOwnerEmail("john@example.com");
        admin.setOwnerAddress("123, Street Name");
    }

    @Test
    void saveAdminDetails() {
        // Mocking the repository behavior
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin savedAdmin = adminService.saveAdminDetails(admin);

        // Verifying the behavior
        assertNotNull(savedAdmin);
        assertEquals(admin.getOwnerName(), savedAdmin.getOwnerName());
        verify(adminRepository, times(1)).save(any(Admin.class));  // Verify that save was called exactly once
    }

    @Test
    void updateAdminDetails() {
        // Mocking getAdminDetails method to return an existing admin
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        Admin updatedAdmin = new Admin();
        updatedAdmin.setOwnerName("Jane Doe");
        updatedAdmin.setOwnerContactNo("0987654321");
        updatedAdmin.setOwnerEmail("jane@example.com");
        updatedAdmin.setOwnerAddress("456, New Street");

        when(adminRepository.save(any(Admin.class))).thenReturn(updatedAdmin);

        Admin result = adminService.updateAdminDetails(updatedAdmin);

        // Verifying the behavior
        assertNotNull(result);
        assertEquals("Jane Doe", result.getOwnerName());
        assertEquals("0987654321", result.getOwnerContactNo());
        verify(adminRepository, times(1)).save(any(Admin.class));  // Verify that save was called once
    }

    @Test
    void getAdminDetails_shouldReturnAdmin() {
        // Mocking the repository behavior
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        Admin foundAdmin = adminService.getAdminDetails(1L);

        // Verifying the behavior
        assertNotNull(foundAdmin);
        assertEquals(admin.getId(), foundAdmin.getId());
        verify(adminRepository, times(1)).findById(1L);  // Verify that findById was called once
    }

    @Test
    void getAdminDetails_shouldThrowExceptionWhenNotFound() {
        // Mocking the repository behavior for non-existing admin
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifying that RuntimeException is thrown
        Exception exception = assertThrows(RuntimeException.class, () -> {
            adminService.getAdminDetails(1L);
        });

        assertEquals("Admin not found", exception.getMessage());
    }
}
