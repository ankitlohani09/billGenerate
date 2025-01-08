package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin/manage")
public class AdminController {

    public static String OWNER_NAME = "SANJAY BORIYA";
    public static String OWNER_CONTACT_NO = "9993957179";
    public static String OWNER_EMAIL = "sanjayboriya13@gmail.com";

    public static String LOGO_PATH = "static/image/LogoRadhikaHotelNoText.png";
    private static final String UPLOAD_DIR = "src/main/resources/static/image/";

    @GetMapping
    public String adminWorkPage(Model model) {
        model.addAttribute("logoPath", LOGO_PATH);
        model.addAttribute("ownerName", OWNER_NAME);
        model.addAttribute("ownerContactNo", OWNER_CONTACT_NO);
        model.addAttribute("ownerEmail", OWNER_EMAIL);
        return "admin/admin-page";
    }

    @PostMapping("/uploadLogo")
    public String uploadLogo(@RequestParam("logoFile") MultipartFile logoFile, Model model) {
        if (!logoFile.isEmpty()) {
            try {
                String fileName = logoFile.getOriginalFilename();
                File destinationFile = new File(UPLOAD_DIR + fileName);
                logoFile.transferTo(destinationFile);
                LOGO_PATH = "static/image/" + fileName;
            } catch (IOException e) {
                model.addAttribute("error", "Logo upload failed: " + e.getMessage());
                return "admin/admin-page";
            }
        } else {
            model.addAttribute("error", "Please select a file to upload.");
            return "admin/admin-page";
        }
        return "redirect:/admin/manage";
    }

    @PostMapping("/updateOwnerDetails")
    public String updateOwnerDetails(@RequestParam("ownerName") String ownerName, @RequestParam("ownerContactNo") String ownerContactNo,
                                     @RequestParam("ownerEmail") String ownerEmail, Model model) {
        OWNER_NAME = ownerName;
        OWNER_CONTACT_NO = ownerContactNo;
        OWNER_EMAIL = ownerEmail;
        model.addAttribute("ownerName", OWNER_NAME);
        model.addAttribute("ownerContactNo", OWNER_CONTACT_NO);
        model.addAttribute("ownerEmail", OWNER_EMAIL);
        return "redirect:/admin/manage";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "/admin/loginPage";
    }
}
