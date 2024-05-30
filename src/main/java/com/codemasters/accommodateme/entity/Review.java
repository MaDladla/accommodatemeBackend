package com.codemasters.accommodateme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@Table(name = "tbl_reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    private String description;

    @Column(name = "posted_at", columnDefinition = "TIMESTAMP")
    private Instant postedAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private Instant updatedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User users;

    @ManyToOne
    @JoinColumn(name = "resId", referencedColumnName = "residence_id", nullable = false)
    private Residence residence;


    public Review() {
        this.postedAt = Instant.now();
        this.updatedAt = Instant.now();
    }

}