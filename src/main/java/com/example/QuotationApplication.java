package com.example;

import com.example.entity.Admin;
import com.example.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.service.impl.AdminServiceImpl.*;

@SpringBootApplication
public class QuotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuotationApplication.class, args);
	}


	@Bean
	CommandLineRunner initializeAdmin(AdminRepository adminRepository) {
		Logger logger = LoggerFactory.getLogger(QuotationApplication.class);

		return args -> {
			Admin existingAdmin = adminRepository.findByUsername("admin");

			if (existingAdmin == null) {
				Admin admin = new Admin();
				admin.setUsername("admin");
				admin.setPassword("admin");
				admin.setOwnerName(OWNER_NAME);
				admin.setOwnerContactNo(OWNER_CONTACT_NO);
				admin.setOwnerEmail(OWNER_EMAIL);
				admin.setOwnerAddress(OWNER_ADDRESS);

				adminRepository.save(admin);
				logger.info("Default admin created!");  // Logging instead of System.out
			} else {
				logger.info("Admin already exists.");
			}
		};
	}
}
