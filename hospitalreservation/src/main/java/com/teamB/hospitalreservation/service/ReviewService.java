package com.teamB.hospitalreservation.service;

import com.teamB.hospitalreservation.entity.HospitalReview;
import com.teamB.hospitalreservation.entity.Review;
import com.teamB.hospitalreservation.repository.HospitalReviewRepository;
import com.teamB.hospitalreservation.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final HospitalReviewRepository hospitalReviewRepository;

    public ReviewService(ReviewRepository reviewRepository, HospitalReviewRepository hospitalReviewRepository) {
        this.reviewRepository = reviewRepository;
        this.hospitalReviewRepository = hospitalReviewRepository;
    }

    public Review addReview(Long hospitalId, int rating, String comment) {
        HospitalReview hospitalReview = hospitalReviewRepository.findById(hospitalId)
            .orElseThrow(() -> new RuntimeException("병원 없음"));

        Review review = new Review();
        review.setHospital(hospitalReview);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    public List<Review> getReviews(Long hospitalId) {
        return reviewRepository.findByHospitalId(hospitalId);
    }

    public double getAverageRating(Long hospitalId) {
        Double avg = reviewRepository.findAverageRatingByHospitalId(hospitalId);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }
    
}