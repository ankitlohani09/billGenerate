package com.example.controllers;

import com.example.entity.Admin;
import com.example.entity.Logo;
import com.example.service.impl.AdminServiceImpl;
import com.example.service.impl.LogoServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;
    private final LogoServiceImpl logoServiceImpl;

    public AdminController(AdminServiceImpl adminServiceImpl, LogoServiceImpl logoServiceImpl) {
        this.adminServiceImpl = adminServiceImpl;
        this.logoServiceImpl = logoServiceImpl;
    }

    @GetMapping("/dashboard")
    public String getAdminDashboard(Model model) {
        Admin admin = adminServiceImpl.getAdminDetails(1L);
        List<Logo> logos = logoServiceImpl.getAllLogos();
        logos.forEach(logo -> model.addAttribute("logoUrl", logo.getLocalLogoPath()));
        model.addAttribute("ownerName", admin.getOwnerName());
        model.addAttribute("ownerContactNo", admin.getOwnerContactNo());
        model.addAttribute("ownerEmail", admin.getOwnerEmail());
        model.addAttribute("ownerAddress", admin.getOwnerAddress());

        return "admin/dashboard";
    }

    @PostMapping("/manage/updateOwnerDetails")
    public String updateOwnerDetails(Admin admin, Model model) {
        Admin response = adminServiceImpl.updateAdminDetails(admin);
        model.addAttribute("ownerName", response.getOwnerName());
        model.addAttribute("ownerContactNo", response.getOwnerContactNo());
        model.addAttribute("ownerEmail", response.getOwnerEmail());
        model.addAttribute("ownerAddress", response.getOwnerAddress());
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/logo/upload")
    public String uploadLogo(@RequestParam("logoFile") MultipartFile logoFile, Model model) {
        try {
            Logo logo = logoServiceImpl.uploadLogo(logoFile);
            if (logo != null) {
                getAdminDashboard(model);
                model.addAttribute("successMessage", "Logo uploaded successfully!");
            }
        } catch (IOException e) {
            model.addAttribute("errorMsg", "Error uploading logo. Please try again.");
        }
        return "admin/dashboard";
    }

    @GetMapping("/adminLoginPage")
    public String loginPage() {
        return "admin/adminLoginPage";
    }
}
