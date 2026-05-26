package com.human.business.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("基础登录服务 API")
                        .version("1.0.0")
                        .description("提供用户注册、登录、登出、密码管理等基础认证功能")
                        .contact(new Contact()
                                .name("basic")
                                .email("")));
    }
}