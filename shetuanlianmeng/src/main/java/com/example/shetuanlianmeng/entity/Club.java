package com.example.shetuanlianmeng.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String date;
    private String author;
    private String category;
    private String president; // 添加 president 字段
    private int currentMembers = 0; // 设置默认值
    private Long userId;

    // 无参构造函数
    public Club() {}

    // 全参构造函数
    public Club(String name, String date, String author, String category, String president) {
        this.name = name;
        this.date = date;
        this.author = author;
        this.category = category;
        this.president = president;
        this.currentMembers = 0; // 初始化字段
    }
}
