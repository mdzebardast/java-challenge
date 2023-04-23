package jp.co.axa.apidemo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
@Import(value = BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("jp.co.axa.apidemo.controllers")).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Employee App REST API",
                "This is a Spring Boot application that provides a REST API for managing employee records. " +
                        "The API allows users to create, retrieve, update, and delete employee records in an in-memory H2 database.",
                "1",
                "Terms of service",
                new Contact("Mohammad Zebardast", "https://www.linkedin.com/in/mohammad-zebardast/", "mdzebardast@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
