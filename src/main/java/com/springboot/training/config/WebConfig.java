package com.springboot.training.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:/usr/local/apache-tomcat101-nic/webapps/filepath/media/")
                .setCachePeriod(3600);
        
        registry.addResourceHandler("/picture/**")
		.addResourceLocations("file:/usr/local/apache-tomcat101-nic/webapps/filepath/pictures/")
		.setCachePeriod(3600);
    }
}