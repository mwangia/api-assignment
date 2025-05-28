package com.interview.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIT {
    @Autowired
    protected TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int serverPort;
    protected String getBaseUrl() {
        return "http://localhost:" + serverPort;
    }
}
