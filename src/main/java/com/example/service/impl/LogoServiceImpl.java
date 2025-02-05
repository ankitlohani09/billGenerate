package com.example.service.impl;

import com.example.entity.Logo;
import com.example.repository.LogoRepository;
import com.example.service.LogoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LogoServiceImpl implements LogoService {

    private final LogoRepository logoRepository;
    private static final Logger logger = LoggerFactory.getLogger(LogoServiceImpl.class);
    public static String LOCAL_LOGO_PATH = "static/image/RE-Solar-Logo.png";
    public static String LOCAL_DUMMYHOTEL_PATH = "static/image/DummyHotelImg.png";

    @PersistenceContext
    private EntityManager entityManager;

    public LogoServiceImpl(LogoRepository logoRepository) {
        this.logoRepository = logoRepository;
    }

    public List<Logo> getAllLogos() {
        return logoRepository.findAll();
    }

    @Transactional
    public Logo uploadLogo(MultipartFile file) throws IOException {
        Optional<Logo> existingLogo = logoRepository.findTopByOrderByIdAsc();

        if (existingLogo.isPresent()) {
            Logo oldLogo = existingLogo.get();
            entityManager.merge(oldLogo);
            logoRepository.delete(oldLogo);
        }

        String newLocalLogoPath = "static/image/" + file.getOriginalFilename();

        Logo newLogo = new Logo();
        newLogo.setLogoName(file.getOriginalFilename());
        newLogo.setLocalLogoPath(newLocalLogoPath);
        newLogo.setUploadedDate(LocalDateTime.now());
        return logoRepository.save(newLogo);
    }

    public Logo getLogoByName(String logoName) {
        logger.info("Fetching logo by name: {}", logoName);
        Optional<Logo> logo = logoRepository.findByLogoName(logoName);
        if (logo.isPresent()) {
            logger.info("Logo found with name: {}", logoName);
            return logo.get();
        } else {
            logger.warn("Logo not found with name: {}", logoName);
            return null;
        }
    }

    public String getLocalLogoPath() {
        List<Logo> allLogos = getAllLogos();

        if (!allLogos.isEmpty()) {  // List empty check
            Logo firstLogo = allLogos.get(0); // Correct way to get first element
            return firstLogo.getLocalLogoPath();
        } else {
            return "No logo available";  // Handle empty list case
        }
    }

}
