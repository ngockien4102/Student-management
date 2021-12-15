package com.student.restTemplate;

import com.student.Token.GetUserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestTemplateService {
    @Value("${service.library.api.host}")
    private String libraryServiceHost;

    private final String subpass = "/int/book/getBorrowBookForStudentService/{username}";

    @Autowired
    GetUserName getUserName;

    @Autowired
    org.springframework.web.client.RestTemplate restTemplate;

    public List<String> getBookBorrow(String userName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(libraryServiceHost);

        Map<String, String> params = new HashMap<>();
        params.put("username", userName);

        List<String> book = restTemplate.getForObject(uriBuilder.toUriString() + subpass, List.class,params);
        return book;
    }
}
