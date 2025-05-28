package com.interview.assignment.controller;

import com.interview.assignment.dto.CustomerDto;
import com.interview.assignment.service.CustomerService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDto customerDto) {
        this.customerService.createCustomer(customerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDto customerDto) {
        this.customerService.updateCustomer(customerDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> patchCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomer = this.customerService.patchCustomer(customerDto);
        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID customerId) {
        this.customerService.deleteCustomer(customerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCustomer(@PathVariable UUID customerId) {
        Optional<CustomerDto> customerDtoOptional = this.customerService.getCustomer(customerId);
        return customerDtoOptional.isPresent() ? ResponseEntity.ok(customerDtoOptional.get())
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDto>> getCustomers(Pageable pageable) {
        List<CustomerDto> customerDtos = this.customerService.getCustomers(pageable);
        return ResponseEntity.ok(customerDtos);
    }
}
