package com.example.repository;

import com.example.entity.Logo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogoRepository extends JpaRepository<Logo, Long> {
    Optional<Logo> findByLogoName(String logoName);
    Optional<Logo> findTopByOrderByIdAsc();
}
