package com.library.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestTemplateService {

    @Value("${service.library.api.host}")
    private String studentServiceHost;

    private final String url_student_id = "/ext/students/{id}";

    @Autowired
    RestTemplate restTemplate;

    public Object getStudentInfo(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(studentServiceHost);

        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        String student = restTemplate.getForObject(uriBuilder.toUriString()+url_student_id, String.class,params);
        return student.toString();
    }
}
