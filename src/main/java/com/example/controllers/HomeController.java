package com.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    public static String OWNER_NAME = "SANJAY BORIYA";
    public static String OFFER_VALIDITY = "10 Days from date of this offer";

    @GetMapping
    public String indexPage(Model model) {
        return "index";
    }

    public static boolean isMobileDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String[] mobileDevices = {"Android", "iPhone", "iPad", "Windows Phone", "BlackBerry", "Opera Mini", "Mobile"};
        if (userAgent != null) {
            for (String device : mobileDevices) {
                if (userAgent.contains(device)) {
                    return true;
                }
            }
        }
        return false;
    }

}
