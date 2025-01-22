package com.example.service;

import com.example.entity.Logo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LogoService {
    List<Logo> getAllLogos();

    // Upload logo and save it to database
    Logo uploadLogo(MultipartFile file) throws IOException;
    Logo getLogoByName(String logoName);
}
