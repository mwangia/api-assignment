package com.interview.assignment.util;

import com.interview.assignment.dto.CustomerDto;
import com.interview.assignment.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerUtil {

    public CustomerDto convertToDto(CustomerEntity customerEntity) {
        return new CustomerDto(customerEntity.getId(), customerEntity.getFirstName(), customerEntity.getMiddleName(),
                customerEntity.getLastName(), customerEntity.getEmail(), customerEntity.getPhoneNumber());
    }

    public CustomerEntity convertToEntity(CustomerDto customerDto) {
        return new CustomerEntity(customerDto.getId(), customerDto.getFirstName(), customerDto.getMiddleName(),
                customerDto.getLastName(), customerDto.getEmail(), customerDto.getPhoneNumber());
    }
}
