package com.example.shetuanlianmeng;

import com.example.shetuanlianmeng.entity.Club;
import com.example.shetuanlianmeng.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShetuanlianmengApplication implements CommandLineRunner {

    @Autowired
    private ClubRepository clubRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShetuanlianmengApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        clubRepository.save(new Club("社团A", "2024-07-05", "张三", "学术类"));
        clubRepository.save(new Club("社团B", "2024-07-05", "李四", "文体类"));
        clubRepository.save(new Club("社团C", "2024-07-05", "王五", "志愿服务类"));
        clubRepository.save(new Club("社团D", "2024-07-05", "刘六", "休闲类"));
    }
}
