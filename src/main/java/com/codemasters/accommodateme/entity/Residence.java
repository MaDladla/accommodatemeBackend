package com.codemasters.accommodateme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "tbl_residence")
public class Residence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "residence_id")
    private Long residenceId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String slogan;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String regNo;
    @Column(nullable = false)
    private int totalNumberOfRooms;
    private int totalNumberOfSingleRooms;
    private int totalNumberOfDoubleRooms;

    private String status = "PENDING";

    private List<String> utility = new ArrayList<>();

    @Column(nullable = false)
    private String profileImage;

    private List<String> images = new ArrayList<>();

    private String nsfasDocument;



    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)
    private User users;

    @OneToMany(mappedBy = "residence", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms;

    @OneToMany(mappedBy = "residence", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Issues> issues;

    @OneToOne(mappedBy = "residence", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Location location;

    @OneToMany(mappedBy = "residence", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;


    @OneToMany(mappedBy = "residence",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "residence",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @Column(name = "applied_at", columnDefinition = "TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy,MM,dd HH:mm:ss")
    private Instant appliedAt;

    public Residence() {
        this.appliedAt = Instant.now();
    }
}
