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





    public Review() {
        this.postedAt = Instant.now();
    }

}