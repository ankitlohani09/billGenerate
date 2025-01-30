package com.example.service;

import com.example.entity.Admin;
import java.util.Optional;

public interface AdminService {
    Admin saveAdminDetails(Admin admin);

    Admin updateAdminDetails(Admin admin);

    Admin getAdminDetails(Long id);
}
