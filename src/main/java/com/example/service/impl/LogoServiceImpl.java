package com.example.service.impl;

import com.example.entity.Logo;
import com.example.repository.LogoRepository;
import com.example.service.LogoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LogoServiceImpl implements LogoService {

    private final LogoRepository logoRepository;
    private static final Logger logger = LoggerFactory.getLogger(LogoServiceImpl.class);
    public static String S3_LOGO_URL = "https://radhika-hotel-s3-bucket.s3.ap-south-1.amazonaws.com/logo/RE-Solar-Logo.png";
    private final S3Client s3Client;
    public static String LOCAL_LOGO_PATH = "static/image/RE-Solar-Logo.png";
    public static String S3_DUMMY_HOTEL_IMG_SRC = "https://radhika-hotel-s3-bucket.s3.amazonaws.com/logo/DummyHotelImg.png";

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @PersistenceContext
    private EntityManager entityManager;

    public LogoServiceImpl(LogoRepository logoRepository) {
        this.logoRepository = logoRepository;
        this.s3Client = S3Client.builder()
                .region(Region.AP_SOUTH_1)
                .build();
    }

    public List<Logo> getAllLogos() {
        return logoRepository.findAll();
    }

    @Transactional
    public Logo uploadLogo(MultipartFile file) throws IOException {
        Optional<Logo> existingLogo = logoRepository.findTopByOrderByIdAsc();
        String fileName = "logo/" + file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

        String s3LogoUrl = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toString();

        if (existingLogo.isPresent()) {
            Logo oldLogo = existingLogo.get();
            entityManager.merge(oldLogo);
            logoRepository.delete(oldLogo);
        }

        S3_LOGO_URL = s3LogoUrl;

        Logo newLogo = new Logo();
        newLogo.setLogoName(file.getOriginalFilename());
        newLogo.setS3Url(s3LogoUrl);
        newLogo.setLocalLogoPath(LOCAL_LOGO_PATH);
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
}
