package com.zosh.modal;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_seq_gen")
    @SequenceGenerator(name = "reviews_seq_gen", sequenceName = "reviews_seq", allocationSize = 1)
    private Long id;

    @Column(name = "review_text", nullable = false)
    private String reviewText;

    @Column(nullable = false)
    private double rating;

    @Column(name = "salon_id", nullable = false)
    private Long salonId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
