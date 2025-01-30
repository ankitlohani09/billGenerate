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
    public static String OWNER_CONTACT_NO = "9993957179,8435555882";
    public static String OWNER_EMAIL = "radhikasolar10@gmail.com";
    public static String OWNER_ADDRESS = "Near Radhika Hotel Dewas Naka Indore M.P.";
    public static String LOGO_PATH = "static/image/RE-Solar-Logo.png";

    @GetMapping("/adminWorkPage")
    public String adminWorkPage(Model model, HttpServletRequest request) {
        model.addAttribute("ownerName", OWNER_NAME);
        model.addAttribute("ownerContactNo", OWNER_CONTACT_NO);
        model.addAttribute("ownerEmail", OWNER_EMAIL);
        model.addAttribute("ownerAddress", OWNER_ADDRESS);
        model.addAttribute("isMobileDevice", isMobileDevice(request));
        return "admin/admin-page";
    }

    @PostMapping("/updateOwnerDetails")
    public String updateOwnerDetails(@RequestParam("ownerName") String ownerName, @RequestParam("ownerContactNo") String ownerContactNo,
                                     @RequestParam("ownerEmail") String ownerEmail, @RequestParam("ownerAddress") String ownerAddress ,
                                     Model model) {
        OWNER_NAME = ownerName;
        OWNER_CONTACT_NO = ownerContactNo;
        OWNER_EMAIL = ownerEmail;
        OWNER_ADDRESS = ownerAddress;
        model.addAttribute("ownerName", OWNER_NAME);
        model.addAttribute("ownerContactNo", OWNER_CONTACT_NO);
        model.addAttribute("ownerEmail", OWNER_EMAIL);
        model.addAttribute("ownerAddress", OWNER_ADDRESS);
        return "redirect:/admin/manage/adminWorkPage";
    }

    @GetMapping("/adminLoginPage")
    public String loginPage() {
        return "admin/adminLoginPage";
    }
}
