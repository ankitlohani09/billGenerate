package com.example.service.impl;

import com.example.entity.Admin;
import com.example.repository.AdminRepository;
import com.example.service.AdminService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

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
