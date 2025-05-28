package com.interview.assignment.util;

import com.interview.assignment.dto.CustomerDto;
import com.interview.assignment.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.UUID;

public class CustomerUtilTest {
    private final CustomerUtil customerUtil = new CustomerUtil();

    @Test
    public void convertToDto()  {
        CustomerEntity customerEntity = new CustomerEntity(UUID.randomUUID(), "John", "Jay", "Johnson", "j@j.com", "555-555-5555");
        CustomerDto customerDto = customerUtil.convertToDto(customerEntity);
        assertCustomerDetails(customerDto, customerEntity);
    }

    @Test
    public void convertToEntity() {
        CustomerDto customerDto = new CustomerDto(UUID.randomUUID(), "John", "Jay", "Johnson", "j@j.com", "555-555-5555");
        CustomerEntity customerEntity = customerUtil.convertToEntity(customerDto);
        assertCustomerDetails(customerDto, customerEntity);
    }


    private void assertCustomerDetails(CustomerDto customerDto, CustomerEntity customerEntity) {
            Assert.isTrue(customerDto.getId().equals(customerEntity.getId()), "Customer ids are not equal");
            Assert.isTrue(customerDto.getFirstName().equals(customerEntity.getFirstName()), "Customer first names are not equal");
            Assert.isTrue(customerDto.getMiddleName().equals(customerEntity.getMiddleName()), "Customer middle names are not equal");
            Assert.isTrue(customerDto.getLastName().equals(customerEntity.getLastName()), "Customer last names are not equal");
            Assert.isTrue(customerDto.getPhoneNumber().equals(customerEntity.getPhoneNumber()), "Customer phone numbers are not equal");
            Assert.isTrue(customerDto.getEmail().equals(customerEntity.getEmail()), "Customer emails are not equal");
        }

}
