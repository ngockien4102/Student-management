package com.library.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class RestTemplateConfig {
//
//        @Value("${service.library.api.host}")
//        private String studentServiceHost;

        @Bean
        public RestTemplate restTemplate(){
            return new RestTemplate();
    }
}
