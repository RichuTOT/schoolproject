package com.example.shetuanlianmeng;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.shetuanlianmeng.config.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class ShetuanlianmengApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(ShetuanlianmengApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
