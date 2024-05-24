package com.codemasters.accommodateme.service;

import com.codemasters.accommodateme.entity.Review;
import com.codemasters.accommodateme.repository.repos.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Service
@AllArgsConstructor
public class ReviewServices {
    private  final ReviewRepository reviewsRepository;

    public Review save(Review theReview) {
        Review review = new Review(
                theReview.getReviewId(),
                theReview.getDescription(),
                Instant.now());
        return reviewsRepository.save(review);

    }


    public void deleteReview(Long id){
        reviewsRepository.deleteById(id);
    }



    public List<Review> getAllReviews(){

        return reviewsRepository.findAll();
    }
}
