package com.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.controllers.HomeController.isMobileDevice;

@Controller
@RequestMapping("/admin/manage")
public class AdminController {

    public static String OWNER_NAME = "SANJAY BORIYA";
    public static String OWNER_CONTACT_NO = "9993957179";
    public static String OWNER_EMAIL = "sanjayboriya13@gmail.com";
    public static String LOGO_PATH = "static/image/RE-Solar-Logo.png";

    @GetMapping("/adminWorkPage")
    public String adminWorkPage(Model model, HttpServletRequest request) {
        model.addAttribute("ownerName", OWNER_NAME);
        model.addAttribute("ownerContactNo", OWNER_CONTACT_NO);
        model.addAttribute("ownerEmail", OWNER_EMAIL);
        model.addAttribute("isMobileDevice", isMobileDevice(request));
        return "admin/admin-page";
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

    @GetMapping("/adminLoginPage")
    public String loginPage() {
        return "admin/adminLoginPage";
    }
}
