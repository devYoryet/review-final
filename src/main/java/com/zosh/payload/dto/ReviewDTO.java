package com.zosh.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {

    private Long id;

    private Long property;

    private UserDTO user;

    private SalonDTO salon;
    private Long salonId; // ✅ AGREGAR salonId también

    private String reviewText;

    private double rating;

    private LocalDateTime createdAt;
}
