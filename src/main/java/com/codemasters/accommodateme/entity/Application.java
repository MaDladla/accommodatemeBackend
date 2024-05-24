package com.codemasters.accommodateme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;
    @Column(length = 65535)
    private String firstName;
    @Column(length = 65535)
    private String lastName;
    @Column(length = 65535)
    private Long idNumber;
    @Column(length = 65535)
    private LocalDate dateOfBirth;
    @Column(length = 65535)
    private String gender;
    @Column(length = 65535)
    private String email;
    @Column(length = 65535)
    private String address;
    @Column(length = 65535)
    private String contactDetails;

    @Column(length = 65535)
    private String guardianFullName;
    @Column(length = 65535)
    private String guardianSurname;
    @Column(length = 65535)
    private String relationship;
    @Column(length = 65535)
    private String guardianEmail;
    @Column(length = 65535)
    private String guardianContacts;
    @Column(length = 65535)
    private String guardianIdocument;

    @Column(length = 65535)
    private String institution;
    @Column(length = 65535)
    private String campus;
    @Column(length = 65535)
    private String bursary;
    @Column(length = 65535)
    private String yearOfStudy;
    @Column(length = 65535)
    private String faculty;

    @Column(length = 65535)
    private String iDocument;
    @Column(length = 65535)
    private String fundDocument;
    @Column(length = 65535)
    private String proofOfReg;
    @Column(length = 65535)
    private String transcript;
    @Column(length = 65535)
    private String studentStatus;
    private String responseMessage;
    private String status;
    @Column(name = "applied_at", columnDefinition = "TIMESTAMP")
    private Date appliedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = true)
    private Room room;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residence_id", referencedColumnName = "residence_id",nullable = true)
    private Residence residence;

}