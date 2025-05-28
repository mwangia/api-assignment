package com.interview.assignment.service;

import com.interview.assignment.dto.CustomerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    void createCustomer(CustomerDto customerDto);
    void updateCustomer(CustomerDto customerDto);
    CustomerDto patchCustomer(CustomerDto customerDto);
    void deleteCustomer(UUID customerId);
    Optional<CustomerDto> getCustomer(UUID customerId);
    List<CustomerDto> getCustomers(Pageable pageable);
}
