package com.zosh.mapper;

import com.zosh.modal.Review;
import com.zosh.payload.dto.ReviewDTO;
import com.zosh.payload.dto.SalonDTO;
import com.zosh.payload.dto.UserDTO;

public class ReviewMapper {

    public static ReviewDTO mapToDTO(Review review, UserDTO userDTO) {
        if (review == null) {
            return null;
        }

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setReviewText(review.getReviewText());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setSalonId(review.getSalonId());
        reviewDTO.setUser(userDTO);
        // reviewDTO.setSalon(salonDTO); // ✅ Salon completo

        reviewDTO.setCreatedAt(review.getCreatedAt());

        return reviewDTO;
    }
}
