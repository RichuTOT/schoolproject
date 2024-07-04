package com.example.shetuanlianmeng.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ClubApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String clubName;
    private String description;
    private String status; // PENDING, APPROVED, REJECTED
}
