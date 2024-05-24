package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.entity.Review;
import com.codemasters.accommodateme.service.ReviewServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/public")
public class ReviewController {
    @Autowired
    private ReviewServices reviewsService;

    @PostMapping("/saveReview")
    public Review submitReview(@RequestBody Review review) {
        return reviewsService.save(review);
    }



    @GetMapping("/getAll")
    @ResponseBody
    public List<Review> getAllReviews(){
        return reviewsService.getAllReviews();
    }

}
