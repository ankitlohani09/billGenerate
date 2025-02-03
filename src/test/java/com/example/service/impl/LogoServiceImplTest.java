package com.example.service.impl;

import com.example.entity.Logo;
import com.example.repository.LogoRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.S3Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoServiceImplTest {

    @Mock
    private LogoRepository logoRepository;

    @Mock
    private S3Client s3Client;

    @Mock
    private S3Utilities s3Utilities;

    @Mock
    private EntityManager entityManager;  // Mock EntityManager

    @InjectMocks
    private LogoServiceImpl logoService;

    private Logo logo1;
    private Logo logo2;
    private MockMultipartFile file;

    private static final String bucketName = "radhika-hotel-s3-bucket";  // Direct value for testing

    @BeforeEach
    void setUp() {
        // Initialize mock data for logos
        logo1 = new Logo();
        logo1.setId(1L);
        logo1.setLogoName("logo1.png");
        logo1.setS3Url("https://s3.amazon.com/logo1.png");
        logo1.setLocalLogoPath("static/image/logo1.png");
        logo1.setUploadedDate(LocalDateTime.now());

        logo2 = new Logo();
        logo2.setId(2L);
        logo2.setLogoName("logo2.png");
        logo2.setS3Url("https://s3.amazon.com/logo2.png");
        logo2.setLocalLogoPath("static/image/logo2.png");
        logo2.setUploadedDate(LocalDateTime.now());

        //Set Value
        ReflectionTestUtils.setField(logoService, "bucketName", bucketName);
        ReflectionTestUtils.setField(logoService, "entityManager", entityManager);

        // Mocking the MultipartFile
        file = new MockMultipartFile("file", "logo.png", "image/png", "logo content".getBytes());
    }

    // Test for getAllLogos method
    @Test
    void getAllLogos_shouldReturnListOfLogos() {
        // Arrange: Mock logoRepository.findAll() to return logos list
        when(logoRepository.findAll()).thenReturn(List.of(logo1, logo2));

        // Act: Call the service method
        List<Logo> logos = logoService.getAllLogos();

        // Assert: Verify the returned list
        assertNotNull(logos);
        assertEquals(2, logos.size());
        assertEquals("logo1.png", logos.get(0).getLogoName());
        assertEquals("logo2.png", logos.get(1).getLogoName());

        // Verify repository interaction
        verify(logoRepository, times(1)).findAll();
    }

    // Test for uploadLogo method
    @Test
    void uploadLogo_shouldUploadAndReturnNewLogo() throws IOException {
        // Arrange: Mock the repository and S3 client
        Optional<Logo> existingLogo = Optional.of(logo1);

        when(logoRepository.findTopByOrderByIdAsc()).thenReturn(existingLogo);

        // Mock S3 client behavior
        InputStream inputStream = file.getInputStream();
        RequestBody requestBody = RequestBody.fromInputStream(inputStream, file.getSize());

        // Ensure that PutObjectRequest includes a valid bucket name
        lenient().when(s3Client.putObject(any(PutObjectRequest.class), eq(requestBody))).thenReturn(null);

        // Mock the S3 client behavior: Mock utilities() to return an instance of S3Utilities
        lenient().when(s3Client.utilities()).thenReturn(s3Utilities);

        // Mock the URL generation from S3 with the correct method signature (Consumer<Builder>)
        lenient().when(s3Utilities.getUrl(any(Consumer.class))).thenReturn(URI.create("https://s3.amazon.com/logo1.png").toURL());

        // Mock the save method to return a logo (we simulate the returned logo here)
        Logo uploadedLogo = new Logo();
        uploadedLogo.setLogoName("logo.png");
        uploadedLogo.setS3Url("https://s3.amazon.com/logo1.png");
        uploadedLogo.setLocalLogoPath("static/image/logo.png");
        uploadedLogo.setUploadedDate(LocalDateTime.now());

        when(logoRepository.save(any(Logo.class))).thenReturn(uploadedLogo);  // Mocking save method

        // Act: Call the service method
        Logo result = logoService.uploadLogo(file);

        // Assert: Verify the new logo properties
        assertNotNull(result);  // Ensure the result is not null
        assertEquals("logo.png", result.getLogoName());
        assertEquals("https://s3.amazon.com/logo1.png", result.getS3Url());
        assertEquals("static/image/logo.png", result.getLocalLogoPath());  // Corrected this assertion

        // Verify repository and S3 interactions
        verify(logoRepository, times(1)).findTopByOrderByIdAsc();
        verify(logoRepository, times(1)).delete(logo1);
        verify(logoRepository, times(1)).save(any(Logo.class));
    }

    // Test for getLogoByName when logo is found
    @Test
    void getLogoByName_shouldReturnLogoWhenFound() {
        // Arrange: Mock logoRepository.findByLogoName() to return a logo
        when(logoRepository.findByLogoName("logo.png")).thenReturn(Optional.of(logo1));

        // Act: Call the service method
        Logo foundLogo = logoService.getLogoByName("logo.png");

        // Assert: Verify the returned logo
        assertNotNull(foundLogo);
        assertEquals("logo1.png", foundLogo.getLogoName());
        assertEquals("https://s3.amazon.com/logo1.png", foundLogo.getS3Url());

        // Verify repository interaction
        verify(logoRepository, times(1)).findByLogoName("logo.png");
    }

    // Test for getLogoByName when logo is not found
    @Test
    void getLogoByName_shouldReturnNullWhenNotFound() {
        // Arrange: Mock logoRepository.findByLogoName() to return an empty Optional
        when(logoRepository.findByLogoName("nonexistent_logo.png")).thenReturn(Optional.empty());

        // Act: Call the service method
        Logo foundLogo = logoService.getLogoByName("nonexistent_logo.png");

        // Assert: Verify the returned value is null
        assertNull(foundLogo);

        // Verify repository interaction
        verify(logoRepository, times(1)).findByLogoName("nonexistent_logo.png");
    }
}
