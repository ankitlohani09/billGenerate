package com.example.controllers;

import com.example.entity.Logo;
import com.example.service.LogoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/logo")
public class LogoController {

    private final LogoService logoService;

    public LogoController(LogoService logoService) {
        this.logoService = logoService;
    }

    @GetMapping("/getAll")
    public List<Logo> getAllLogos() {
        return logoService.getAllLogos();
    }

    // Endpoint to upload logo
    @PostMapping("/upload")
    public String uploadLogo(@RequestParam("logoFile") MultipartFile file, Model model) {
        try {
            Logo logo = logoService.uploadLogo(file);
            model.addAttribute("successMessage", "Logo uploaded successfully!");
            model.addAttribute("logo", logo);
        } catch (IOException e) {
            model.addAttribute("errorMsg", "Error uploading logo. Please try again.");
        }
        return "admin/admin-page";
    }

    @GetMapping("/name/{logoName}")
    public ResponseEntity<Logo> getLogoByName(@PathVariable String logoName) {
        Logo logo = logoService.getLogoByName(logoName);
        if (logo != null) {
            return new ResponseEntity<>(logo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
