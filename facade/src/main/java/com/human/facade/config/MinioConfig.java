package com.human.facade.config;

import com.human.facade.pojo.MinioPojo;
import io.minio.MinioClient;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class MinioConfig {

    @Resource
    private MinioPojo minioPojo;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioPojo.getUrl())
                .credentials(minioPojo.getUsername(), minioPojo.getPassword())
                .build();
    }
}
