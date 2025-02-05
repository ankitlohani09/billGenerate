package com.example.controllers;

import com.example.config.JwtService;
import com.example.dto.AuthRequest;
import com.example.dto.JwtResponse;
import com.example.dto.RefreshTokenRequest;
import com.example.entity.Admin;
import com.example.entity.Role;
import com.example.repository.AdminRepository;
import com.example.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    // Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Admin adminRequest) {
        try {
            // Check if the username already exists
            if (adminRepository.existsByUsername(adminRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
            }

            // Password ko hash karo
            String hashedPassword = passwordEncoder.encode(adminRequest.getPassword());
            adminRequest.setPassword(hashedPassword);

            // Fetch or create the ROLE_ADMIN
            Role adminRole = roleRepository.findByName("ADMIN");  // Assuming you have a "Role" with "ADMIN" name
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ADMIN");
                roleRepository.save(adminRole);  // Save it if not present
            }

            // Set the roles for the admin
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            adminRequest.setRoles(roles);

            // Admin ko save karte hain database mein
            Admin savedAdmin = adminRepository.save(adminRequest);

            // JWT tokens generate karte hain
            String accessToken = jwtService.generateToken(savedAdmin.getUsername(), true);
            String refreshToken = jwtService.generateToken(savedAdmin.getUsername(), false);

            // Response return karte hain
            JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken, savedAdmin);
            return ResponseEntity.status(HttpStatus.CREATED).body(jwtResponse);
        } catch (Exception e) {
            logger.error("Error during registration", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Generate tokens
            String accessToken = jwtService.generateToken(authRequest.getUsername(), true);
            String refreshToken = jwtService.generateToken(authRequest.getUsername(), false);

            // Fetch admin details
            Admin admin = adminRepository.findByUsername(authRequest.getUsername());
            if (admin == null) {
                throw new RuntimeException("Admin not found");
            }
            // Create response
            JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken, admin);
            return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException e) {
            logger.error("Invalid username or password for user: {}", authRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            logger.error("Error during authentication for user: {}", authRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication");
        }
    }

    @PostMapping("refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        if (jwtService.validateToken(request.refreshToken())) {
            String usernameFromToken = jwtService.getUsernameFromToken(request.refreshToken());
            String accessToken = jwtService.generateToken(usernameFromToken, true);
            String refreshToken = jwtService.generateToken(usernameFromToken, false);
            Admin admin = adminRepository.findByUsername(usernameFromToken);
            JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken, admin);
            return ResponseEntity.ok(jwtResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }
}