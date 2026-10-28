package com.oj.medicore.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class PatientRequest {

    @NotBlank(message = "Patient name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotNull(message = "Date of birth is required")
    @PastOrPresent(message = "Date of birth cannot be in the future")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone Number is Required")
    @Size(max = 30, message = "Phone number cannot exceed 30 characters")
    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must contain 10 to 15 digits"
    )
    private String phone;

    @NotBlank(message = "Email is Required")
    @Email(message = "Email format is invalid")
    @Size(max = 254, message = "Email Cannot exceed 254 characters")
    private String email;

    @NotBlank(message = "Gender is Required")
    @Size(max = 30, message = "Gender cannot exceed 30 characters")
    @Pattern(
            regexp = "(?i)^(MALE|FEMALE|OTHER)$",
            message = "Gender must be MALE, FEMALE, or OTHER"
    )
    private String gender;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    public PatientRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}