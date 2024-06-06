package com.codemasters.accommodateme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class ApplicationDto {

    @NotBlank
    @NotNull
    private Long id;
    @NotBlank
    @NotNull
    private String firstName;
    @NotBlank
    @NotNull
    private String lastName;
    @NotBlank
    @NotNull
    private Long idNumber;
    @NotBlank
    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank
    @NotNull
    private String gender;
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String address;
    @NotBlank
    @NotNull
    private String contactDetails;

    @NotBlank
    @NotNull
    private String guardianFullName;
    @NotBlank
    @NotNull
    private String guardianSurname;
    @NotBlank
    @NotNull
    private String relationship;
    @NotBlank
    @NotNull
    private String guardianEmail;
    @NotBlank
    @NotNull
    private String guardianContacts;
    @NotBlank
    @NotNull
    private String guardianIdocument;

    @NotBlank
    @NotNull
    private String institution;
    @NotBlank
    @NotNull
    private String campus;
    @NotBlank
    @NotNull
    private String bursary;
    @NotBlank
    @NotNull
    private String yearOfStudy;
    @NotBlank
    @NotNull
    private String faculty;

    @NotBlank
    @NotNull
    private String iDocument;
    @NotBlank
    @NotNull
    private String fundDocument;
    @NotBlank
    @NotNull
    private String proofOfReg;
    @NotBlank
    @NotNull
    private String transcript;
    @NotBlank
    @NotNull
    private String studentStatus;
    @NotBlank
    @NotNull
    private String responseMessage;
    @NotBlank
    @NotNull
    private String status;
    @NotBlank
    @NotNull
    private Date appliedAt;
    @NotBlank
    @NotNull
    private String rejectionReason;

    @NotBlank
    @NotNull
    private String room;

    @NotBlank
    @NotNull
    private String user;

    @NotBlank
    @NotNull
    private String residence;
}
