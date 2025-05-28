package com.interview.assignment.impl;

import com.interview.assignment.dto.CustomerDto;
import com.interview.assignment.entity.CustomerEntity;
import com.interview.assignment.exception.NotFoundException;
import com.interview.assignment.repository.CustomerRepository;
import com.interview.assignment.service.impl.CustomerServiceImpl;
import com.interview.assignment.util.CustomerUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerUtil customerUtil;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerDto customerDto;
    private CustomerEntity customerEntity;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();

        customerDto = new CustomerDto();
        customerDto.setId(customerId);
        customerDto.setFirstName("John");
        customerDto.setLastName("Doe");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setPhoneNumber("123-456-7890");

        customerEntity = new CustomerEntity();
        customerEntity.setId(customerId);
        customerEntity.setFirstName("John");
        customerEntity.setLastName("Doe");
        customerEntity.setEmail("john.doe@example.com");
        customerEntity.setPhoneNumber("123-456-7890");
    }

    @Test
    void createCustomer_ValidInput_CallsSave() {
        when(customerUtil.convertToEntity(customerDto)).thenReturn(customerEntity);
        customerService.createCustomer(customerDto);

        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void updateCustomer_ExistingCustomer_CallsSave() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));

        customerService.updateCustomer(customerDto);

        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void updateCustomer_NonExistingCustomer_ThrowsNotFoundException() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.updateCustomer(customerDto));

        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void updateCustomer_CustomerIdNull_ThrowsNotFoundException() {
        customerDto.setId(null);

        assertThrows(NotFoundException.class, () -> customerService.updateCustomer(customerDto));

        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void patchCustomer_ExistingCustomer_CallsSaveAndReturnsDto() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
        when(customerUtil.convertToDto(customerEntity)).thenReturn(customerDto);

        CustomerDto patchedDto = customerService.patchCustomer(customerDto);

        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
        assertNotNull(patchedDto);
        assertEquals(customerDto.getFirstName(), patchedDto.getFirstName());
    }

    @Test
    void patchCustomer_NonExistingCustomer_ThrowsNotFoundException() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.patchCustomer(customerDto));

        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void deleteCustomer_ExistingCustomer_CallsDeleteById() {
        when(customerRepository.existsById(customerId)).thenReturn(true);

        customerService.deleteCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void deleteCustomer_NonExistingCustomer_ThrowsNotFoundException() {
        when(customerRepository.existsById(customerId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> customerService.deleteCustomer(customerId));

        verify(customerRepository, never()).deleteById(customerId);
    }

    @Test
    void getCustomer_ExistingCustomer_ReturnsDto() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(customerUtil.convertToDto(customerEntity)).thenReturn(customerDto);
        Optional<CustomerDto> returnedCustomer = customerService.getCustomer(customerId);

        verify(customerRepository, times(1)).findById(customerId);
        assertTrue(returnedCustomer.isPresent());
        assertEquals(customerDto.getFirstName(), returnedCustomer.get().getFirstName());
    }

    @Test
    void getCustomer_NonExistingCustomer_ReturnsEmptyOptional() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Optional<CustomerDto> customerDtoOptional = customerService.getCustomer(customerId);

        verify(customerRepository, times(1)).findById(customerId);
        assertTrue(customerDtoOptional.isEmpty());
    }

    @Test
    void getCustomers_ReturnsListOfDtos() {
        Pageable pageable = Pageable.unpaged();
        List<CustomerEntity> customerEntities = Collections.singletonList(customerEntity);
        Page<CustomerEntity> customerEntityPage = new PageImpl<>(customerEntities, pageable, customerEntities.size());

        when(customerRepository.findAll(pageable)).thenReturn(customerEntityPage);

        when(customerUtil.convertToDto(customerEntity)).thenReturn(customerDto);

        List<CustomerDto> customerDtoList = customerService.getCustomers(pageable);

        assertFalse(customerDtoList.isEmpty());
        assertEquals(1, customerDtoList.size());
        assertEquals(customerDto.getFirstName(), customerDtoList.get(0).getFirstName());
    }
}

