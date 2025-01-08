package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    public static String OWNER_NAME = "SANJAY BORIYA";
//    public static String PLANT_CAPACITY = "03 KW";
//    public static String ON_OFF_GRID = "on grid";
    public static String OFFER_VALIDITY = "10 Days from date of this offer";

    @GetMapping
    public String indexPage(Model model) {
        return "index";
    }

}
