package com.example.railwayticket.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfiguration {
    private static final String SECURITY_SCHEME_NAME = "JWT Token";

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(apiInfo());
        openAPI.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
        openAPI.components(apiComponents());
        return openAPI;
    }

    private Info apiInfo() {
        Info info = new Info();
        info.setTitle("Spring boot test");
        info.setDescription("Spring-boot application");
        info.setContact(apiContact());
        info.setVersion("0.0.1");
        info.setLicense(apiLicense());
        info.setTermsOfService("Terms of Services");
        return info;
    }

    private Contact apiContact() {
        Contact contact = new Contact();
        contact.setName("Spring boot test");
        contact.setEmail("spring@gmail.com");
        contact.setUrl("https://spring.com");
        return contact;
    }

    private License apiLicense() {
        License license = new License();
        license.setName("Spring boot test");
        license.setUrl("https://spring.com");
        return license;
    }

    private Components apiComponents() {
        Components components = new Components();
        components.addSecuritySchemes(
            SECURITY_SCHEME_NAME,
            new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
        );

        return components;
    }
}
