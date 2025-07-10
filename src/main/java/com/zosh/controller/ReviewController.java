package com.zosh.controller;

import com.zosh.exception.UserException;
import com.zosh.mapper.ReviewMapper;
import com.zosh.modal.Review;

import com.zosh.payload.dto.ReviewDTO;
import com.zosh.payload.dto.SalonDTO;
import com.zosh.payload.dto.UserDTO;
import com.zosh.payload.request.CreateReviewRequest;
import com.zosh.payload.response.ApiResponse;

import com.zosh.service.ReviewService;
import com.zosh.service.clients.SalonFeignClient;
import com.zosh.service.clients.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserFeignClient userService;
    private final SalonFeignClient salonService;

    @PostMapping("/salon/{salonId}")
    public ResponseEntity<ReviewDTO> writeReview(
            @RequestBody CreateReviewRequest req,
            @PathVariable Long salonId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        System.out.println("🔍 REVIEW CONTROLLER - Creating review for salon: " + salonId);

        try {
            // 1. Obtener usuario
            UserDTO user = userService.getUserFromJwtToken(jwt).getBody();
            System.out.println("✅ Usuario obtenido: " + user.getId());

            // 2. Obtener salon
            SalonDTO salon = salonService.getSalonById(salonId, jwt).getBody();
            System.out.println("✅ Salon obtenido: " + salon.getId());

            // 3. Crear review
            Review review = reviewService.createReview(req, user, salon);
            System.out.println("✅ Review creado: " + review.getId());

            // 4. ✅ SOLUCIÓN: Usar el usuario que ya tenemos, no volver a consultarlo
            ReviewDTO reviewDTO = ReviewMapper.mapToDTO(review, user); // Solo 2 parámetros

            System.out.println("✅ ReviewDTO creado exitosamente");
            return ResponseEntity.ok(reviewDTO);

        } catch (Exception e) {
            System.err.println("❌ Error creando review: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @RequestBody CreateReviewRequest req,
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        UserDTO user = userService.getUserFromJwtToken(jwt).getBody();

        Review review = reviewService.updateReview(
                reviewId,
                req.getReviewText(),
                req.getReviewRating(),
                user.getId());
        return ResponseEntity.ok(review);

    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userService.getUserFromJwtToken(jwt).getBody();

        reviewService.deleteReview(reviewId, user.getId());
        ApiResponse res = new ApiResponse("Review deleted successfully");

        return ResponseEntity.ok(res);

    }
}
