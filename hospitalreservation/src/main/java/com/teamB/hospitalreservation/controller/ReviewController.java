package com.teamB.hospitalreservation.controller;

import com.teamB.hospitalreservation.dto.ReviewDto;
import com.teamB.hospitalreservation.entity.Review;
import com.teamB.hospitalreservation.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital-reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 리뷰 등록
    @PostMapping("/{hospitalReviewId}/reviews")
    public ResponseEntity<?> addReview(@PathVariable Long hospitalReviewId, @RequestBody ReviewDto dto) {
        reviewService.addReview(hospitalReviewId, dto.getRating(), dto.getComment());
        return ResponseEntity.ok("리뷰 등록 완료");
    }

    // 리뷰 조회
    @GetMapping("/{hospitalReviewId}/reviews")
    public List<Review> getReviews(@PathVariable Long hospitalReviewId) {
        return reviewService.getReviews(hospitalReviewId);
    }

    // 평균 평점 조회
    @GetMapping("/{hospitalReviewId}/rating")
    public double getAverageRating(@PathVariable Long hospitalReviewId) {
        return reviewService.getAverageRating(hospitalReviewId);
    }
}