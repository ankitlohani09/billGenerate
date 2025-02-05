package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Logo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logoName;
    private String localLogoPath;
    private LocalDateTime uploadedDate;

    @Version
    private int version;

    public Logo() {
    }

    public Logo(String logoName, String localLogoPath, LocalDateTime uploadedDate) {
        this.logoName = logoName;
        this.localLogoPath = localLogoPath;
        this.uploadedDate = uploadedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getLocalLogoPath() {
        return localLogoPath;
    }

    public void setLocalLogoPath(String localLogoPath) {
        this.localLogoPath = localLogoPath;
    }

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
