package com.stepproject.ibatechurlshortener.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.stream.IntStream;

@Configuration
@EnableWebMvc
public class ConfigurerApp implements WebMvcConfigurer {
    private static final String PREFIX = "classpath:/templates";

    private static final String[] MAPPINGS = {"/css/**", "/js/**", "/img/**"};

    private static final String[] LOCATIONS = {"/css/", "/js/", "/img/"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (MAPPINGS.length != LOCATIONS.length) throw new IllegalArgumentException(
                "Configurer:Mapping size must be equal to the Location size"
        );
        IntStream.range(0, MAPPINGS.length)
                .forEach(i -> registry.addResourceHandler(MAPPINGS[i])
                        .addResourceLocations(PREFIX + LOCATIONS[i]));
    }
}
