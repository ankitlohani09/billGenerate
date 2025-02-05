package com.example.service.impl;

import com.example.entity.Admin;
import com.example.repository.AdminRepository;
import com.example.service.AdminService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    public static String OWNER_NAME = "SANJAY BORIYA";
    public static String OWNER_CONTACT_NO = "9993957179,8435555882";
    public static String OWNER_EMAIL = "radhikasolar10@gmail.com";
    public static String OWNER_ADDRESS = "Near Radhika Hotel Dewas Naka Indore M.P.";

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin saveAdminDetails(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdminDetails(Admin admin) {
        Admin existAdmin = getAdminDetails(1L);
        existAdmin.setOwnerName(admin.getOwnerName());
        existAdmin.setOwnerContactNo(admin.getOwnerContactNo());
        existAdmin.setOwnerEmail(admin.getOwnerEmail());
        existAdmin.setOwnerAddress(admin.getOwnerAddress());
        return adminRepository.save(existAdmin);
    }

    @Override
    public Admin getAdminDetails(Long id) {
        Optional<Admin> adminOpt = adminRepository.findById(id);
        if (adminOpt.isPresent()) {
            return adminOpt.get();
        } else {
            throw new RuntimeException("Admin not found");
        }
    }
}
