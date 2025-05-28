package com.interview.assignment.controller;

import com.interview.assignment.BaseIT;
import com.interview.assignment.dto.CustomerDto;
import com.interview.assignment.entity.CustomerEntity;
import com.interview.assignment.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerControllerIT extends BaseIT {
    @Autowired
    protected CustomerRepository customerRepository;
    private static final String WRONG_HTTP_RESPONSE_CODE = "Wrong HTTP response status code";
    private static final String FIRST_NAMES_DONT_MATCH = "First names don't match";
    private static final String MIDDLE_NAMES_DONT_MATCH = "Middle names don't match";
    private static final String LAST_NAMES_DONT_MATCH = "Last names don't match";
    private static final String EMAILS_DONT_MATCH = "Emails don't match";
    private static final String PHONES_DONT_MATCH = "Phone numbers don't match";

    @BeforeEach
    public void initialize() {
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateCustomer() {
        CustomerDto customerDto = createCustomerDto();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(getBaseUrl() + "/v1/customers",
                HttpMethod.POST, new HttpEntity<>(customerDto), Void.class);
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.CREATED), WRONG_HTTP_RESPONSE_CODE);
        CustomerEntity savedCustomerEntity = this.customerRepository.findAll().get(0);
        assertCustomerInfo(customerDto, savedCustomerEntity);

    }

    private CustomerDto createCustomerDto() {
        return new CustomerDto(null, "John", "Jay", "Johnson", "j@j.com", "555-555-5555");
    }

    @Test
    public void testUpdateCustomer() {
        CustomerEntity originalCustomerEntity = saveCustomerEntityToDb();
        CustomerDto updatedCustomerDto = new CustomerDto(originalCustomerEntity.getId(), "Albert", "Al", "Albertson", "a@a.com", "222-222-222");
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(getBaseUrl() + "/v1/customers",
                HttpMethod.PUT, new HttpEntity<>(updatedCustomerDto), Void.class);
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.OK), WRONG_HTTP_RESPONSE_CODE);
        CustomerEntity savedCustomerEntity = this.customerRepository.findById(updatedCustomerDto.getId()).get();
        assertCustomerInfo(updatedCustomerDto, savedCustomerEntity);
    }

    private void assertCustomerInfo(CustomerDto customerDto, CustomerEntity customerEntity) {
        Assert.isTrue(customerDto.getFirstName().equals(customerEntity.getFirstName()), FIRST_NAMES_DONT_MATCH);
        Assert.isTrue(customerDto.getMiddleName().equals(customerEntity.getMiddleName()), MIDDLE_NAMES_DONT_MATCH);
        Assert.isTrue(customerDto.getLastName().equals(customerEntity.getLastName()), LAST_NAMES_DONT_MATCH);
        Assert.isTrue(customerDto.getEmail().equals(customerEntity.getEmail()), EMAILS_DONT_MATCH);
        Assert.isTrue(customerDto.getPhoneNumber().equals(customerEntity.getPhoneNumber()), PHONES_DONT_MATCH);
    }

    @Test
    public void testPatchUpdateCustomer() {
        CustomerEntity originalCustomerEntity = saveCustomerEntityToDb();
        CustomerDto patchUpdatedCustomerDto = new CustomerDto();
        patchUpdatedCustomerDto.setId(originalCustomerEntity.getId());
        patchUpdatedCustomerDto.setPhoneNumber("333-333-3333");
        ResponseEntity<CustomerDto> responseEntity = testRestTemplate.exchange(getBaseUrl() + "/v1/customers",
                HttpMethod.PATCH, new HttpEntity<>(patchUpdatedCustomerDto), CustomerDto.class);
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.OK), WRONG_HTTP_RESPONSE_CODE);
        CustomerDto updatedCustomerDto = responseEntity.getBody();
        CustomerEntity savedCustomerEntity = this.customerRepository.findById(updatedCustomerDto.getId()).get();
        assertCustomerInfo(updatedCustomerDto, savedCustomerEntity);
    }


    @Test
    public void testGetCustomer() {
        CustomerEntity customerEntity = saveCustomerEntityToDb();
        ResponseEntity<CustomerDto> responseEntity = testRestTemplate.getForEntity(getBaseUrl() + "/v1/customers/{id}",
                CustomerDto.class, customerEntity.getId());
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.OK), WRONG_HTTP_RESPONSE_CODE);
        CustomerDto customerDto = responseEntity.getBody();
        assertCustomerInfo(customerDto, customerEntity);
    }

    @Test
    public void testDeleteCustomer() {
        CustomerEntity originalCustomerEntity = saveCustomerEntityToDb();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                getBaseUrl() + "/v1/customers/{id}",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class,
                originalCustomerEntity.getId()
        );
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.OK), WRONG_HTTP_RESPONSE_CODE);
        Optional<CustomerEntity> customerEntityOptional = this.customerRepository.findById(originalCustomerEntity.getId());
        Assert.isTrue(customerEntityOptional.isEmpty(), "Customer was not deleted");
    }

    @Test
    public void testGetCustomers() {
        List<CustomerEntity> customerEntities = saveCustomerEntitiesToDb();
        ResponseEntity<List<CustomerDto>> responseEntity = testRestTemplate.exchange(
                getBaseUrl() + "/v1/customers",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {}
        );
        Assert.isTrue(responseEntity.getStatusCode().equals(HttpStatus.OK), WRONG_HTTP_RESPONSE_CODE);
        List<CustomerDto> customerDtos = responseEntity.getBody();
        Assert.isTrue(customerDtos.size() == 2, "Invalid number of customers");
        customerDtos.forEach( customerDto -> {
            assertCustomerInfo(customerDto,this.customerRepository.findById(customerDto.getId()).get());
        });
    }

    private CustomerEntity saveCustomerEntityToDb() {
        return this.customerRepository.save(new CustomerEntity(null, "John", "Jay",
                "Johnson", "j@j.com", "555-555-5555"));
    }

    private List<CustomerEntity> saveCustomerEntitiesToDb() {
        List<CustomerEntity> customerEntities = new ArrayList<>();
        customerEntities.add(new CustomerEntity(null, "John", "Jay",
                "Johnson", "j@j.com", "555-555-5555"));
        customerEntities.add(new CustomerEntity(null, "Albert", "Al",
                "Albertson", "a@a.com", "222-222-2222"));
        return this.customerRepository.saveAll(customerEntities);
    }
}
