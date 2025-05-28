package com.interview.assignment.service.impl;

import com.interview.assignment.dto.CustomerDto;
import com.interview.assignment.entity.CustomerEntity;
import com.interview.assignment.exception.NotFoundException;
import com.interview.assignment.repository.CustomerRepository;
import com.interview.assignment.service.CustomerService;
import com.interview.assignment.util.CustomerUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerUtil customerUtil;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerUtil customerUtil) {
        this.customerRepository = customerRepository;
        this.customerUtil = customerUtil;
    }
    @Override
    public void createCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = customerUtil.convertToEntity(customerDto);
        this.customerRepository.save(customerEntity);
    }

    @Override
    public void updateCustomer(CustomerDto customerDto) {
        UUID customerId = customerDto.getId();
        if (customerId == null) {
            throw new NotFoundException("Customer Id is required for update but it is not present");
        } else {
            Optional<CustomerEntity> customerEntityOptional = this.customerRepository.findById(customerId);
            if (customerEntityOptional.isPresent()) {
                CustomerEntity existingCustomerEntity = customerEntityOptional.get();
                existingCustomerEntity.setFirstName(customerDto.getFirstName());
                existingCustomerEntity.setMiddleName(customerDto.getMiddleName());
                existingCustomerEntity.setLastName(customerDto.getLastName());
                existingCustomerEntity.setEmail(customerDto.getEmail());
                existingCustomerEntity.setPhoneNumber(customerDto.getPhoneNumber());
                this.customerRepository.save(existingCustomerEntity);
            } else {
                throw new NotFoundException("Customer for update not found");
            }
        }
    }

    @Override
    public CustomerDto patchCustomer(CustomerDto customerDto) {
        UUID customerId = customerDto.getId();
        if (customerId == null) {
            throw new NotFoundException("Customer Id is required for patch update but it is not present");
        }

        Optional<CustomerEntity> customerEntityOptional = this.customerRepository.findById(customerId);

        if (customerEntityOptional.isEmpty()) {
            throw new NotFoundException("Customer for patch update not found");
        }

        CustomerEntity existingCustomerEntity = updateCustomerEntityFields(customerDto, customerEntityOptional);

        CustomerEntity updatedCustomerEntity = this.customerRepository.save(existingCustomerEntity);
        return customerUtil.convertToDto(updatedCustomerEntity);
    }

    private static CustomerEntity updateCustomerEntityFields(CustomerDto customerDto,
                                                             Optional<CustomerEntity> customerEntityOptional) {
        CustomerEntity existingCustomerEntity = customerEntityOptional.get();
        if (customerDto.getFirstName() != null) {
            existingCustomerEntity.setFirstName(customerDto.getFirstName());
        }
        if (customerDto.getMiddleName() != null) {
            existingCustomerEntity.setMiddleName(customerDto.getMiddleName());
        }
        if (customerDto.getLastName() != null) {
            existingCustomerEntity.setLastName(customerDto.getLastName());
        }
        if (customerDto.getPhoneNumber() != null) {
            existingCustomerEntity.setPhoneNumber(customerDto.getPhoneNumber());
        }
        if (customerDto.getEmail() != null) {
            existingCustomerEntity.setEmail(customerDto.getEmail());
        }
        return existingCustomerEntity;
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        if (!this.customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer to be deleted was not found");
        }
        this.customerRepository.deleteById(customerId);
    }

    @Override
    public Optional<CustomerDto> getCustomer(UUID customerId) {
        Optional<CustomerEntity> customerEntityOptional = this.customerRepository.findById(customerId);
        return customerEntityOptional.map(customerUtil::convertToDto);
    }

    @Override
    public List<CustomerDto> getCustomers(Pageable pageable) {
        return this.customerRepository.findAll(pageable).stream().map(customerUtil::convertToDto).toList();
    }
}
