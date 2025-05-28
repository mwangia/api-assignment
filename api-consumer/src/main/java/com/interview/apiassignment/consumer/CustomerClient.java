package com.interview.apiassignment.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.apiassignment.consumer.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerClient {
    private static final Logger log = LoggerFactory.getLogger(CustomerClient.class);
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public CustomerClient(String url) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.baseUrl = url;
    }

    public List<CustomerDto> listCustomers() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<CustomerDto> customers = objectMapper.readValue(response.body(), new TypeReference<>() {});
        log.info("Fetched customers from the GET /v1/customers endpoint:");
        if (customers.isEmpty()) {
            log.info("no customers found");
        } else {
            customers.forEach(System.out::println);
        }

        return customers;
    }

    public void createCustomer(CustomerDto dto) throws Exception {
        String requestBody = objectMapper.writeValueAsString(dto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        log.info("Creating a customer using the POST /v1/customers endpoint:");
        int statusCode = response.statusCode();
        if (statusCode == 201) {
            log.info("Customer created. Status: " + statusCode);
        } else {
            log.error("Error:" + statusCode);
        }
    }

    public void updateCustomer(CustomerDto dto) throws Exception {
        String requestBody = objectMapper.writeValueAsString(dto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        log.info("Updating a customer using the PUT /v1/customers endpoint:");
        int statusCode = response.statusCode();
        if (statusCode == 200) {
            log.info("Customer updated. Status: " + statusCode);
        } else {
            log.error("Error:" + statusCode);
        }
    }

    public Optional<CustomerDto> patchUpdateCustomer(CustomerDto dto) throws Exception {
        String requestBody = objectMapper.writeValueAsString(dto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Updating a customer using the PATCH /v1/customers endpoint:");
        int statusCode = response.statusCode();
        String responseBody = response.body();
        if (statusCode == 200) {
            CustomerDto customer = objectMapper.readValue(responseBody, CustomerDto.class);
            log.info("Customer patch updated:" + customer);
            return Optional.of(customer);
        } else {
            log.error("Error:" + statusCode);
            return Optional.empty();
        }

    }

    public Optional<CustomerDto> getCustomer(UUID id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        int statusCode = response.statusCode();
        if (statusCode == 200) {
            CustomerDto customer = objectMapper.readValue(responseBody, CustomerDto.class);
            log.info("Customer: " + customer);
            return Optional.of(customer);
        } else {
            log.error("Error status code: " + statusCode);
            return Optional.empty();
        }
    }

    public void deleteCustomer(UUID id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        log.info("Deleting customer using the DELETE /v1/customers endpoint");
        log.info("Status code: " + statusCode);
    }
}

