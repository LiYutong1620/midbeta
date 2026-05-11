package com.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j 配置类
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("新闻资讯发布与推荐系统 API文档")
                        .description("我的新闻资讯系统后台接口文档")
                        .version("v1.0.0")
                        .contact(new Contact().name("Tong").email("tong@example.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
