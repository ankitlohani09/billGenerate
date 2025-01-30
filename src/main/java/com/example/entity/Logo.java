package com.example.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Logo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logoName;

    private String s3Url;

    private String localLogoPath;

    private LocalDateTime uploadedDate;

    @Version
    private int version;

    public Logo() {
    }

    public Logo(String logoName, String s3Url, LocalDateTime uploadedDate) {
        this.logoName = logoName;
        this.s3Url = s3Url;
        this.uploadedDate = uploadedDate;
    }

    public String getLocalLogoPath() {
        return localLogoPath;
    }

    public void setLocalLogoPath(String localLogoPath) {
        this.localLogoPath = localLogoPath;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public String getS3Url() {
        return s3Url;
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }
}
