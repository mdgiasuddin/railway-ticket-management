package com.example.railwayticket.configuration.swagger;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SwaggerSecurityConfiguration {

    @Bean
    public FilterRegistrationBean<SwaggerBasicAuthFilter> swaggerBasicAuthFilterRegistration(PasswordEncoder passwordEncoder) {
        FilterRegistrationBean<SwaggerBasicAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SwaggerBasicAuthFilter(passwordEncoder));
        registrationBean.addUrlPatterns("/swagger/*", "/v2/api-docs", "/swagger-ui.html");
        registrationBean.setName("swaggerBasicAuthFilterRegistration");
        return registrationBean;
    }
}
