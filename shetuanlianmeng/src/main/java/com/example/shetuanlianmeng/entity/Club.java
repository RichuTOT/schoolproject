package com.example.shetuanlianmeng.entity;

import lombok.Data;
import jakarta.persistence.*;
//import java.util.List;

@Entity
@Data
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String leader;
    private String category;
    private int currentMembers;
    private String requirements;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
