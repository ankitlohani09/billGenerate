package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_detail")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String ownerName;
    private String ownerContactNo;
    private String ownerEmail;
    private String ownerAddress;

    // Constructors
    public Admin() {
    }

    public Admin(Long id, String username, String password, String ownerName, String ownerContactNo, String ownerEmail, String ownerAddress) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.ownerName = ownerName;
        this.ownerContactNo = ownerContactNo;
        this.ownerEmail = ownerEmail;
        this.ownerAddress = ownerAddress;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerContactNo() {
        return ownerContactNo;
    }

    public void setOwnerContactNo(String ownerContactNo) {
        this.ownerContactNo = ownerContactNo;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }
}
