package com.interview.assignment.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerDtoTest {

    @Test
    void testDefaultConstructor() {
        // When
        CustomerDto customerDto = new CustomerDto();

        // Then
        assertThat(customerDto.getId()).isNull();
        assertThat(customerDto.getFirstName()).isNull();
        assertThat(customerDto.getMiddleName()).isNull();
        assertThat(customerDto.getLastName()).isNull();
        assertThat(customerDto.getEmail()).isNull();
        assertThat(customerDto.getPhoneNumber()).isNull();
    }

    @Test
    void testParameterizedConstructor() {
        // Given
        UUID id = UUID.randomUUID();
        String firstName = "John";
        String middleName = "Michael";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phoneNumber = "555-1234";

        // When
        CustomerDto customerDto = new CustomerDto(id, firstName, middleName, lastName, email, phoneNumber);

        // Then
        assertThat(customerDto.getId()).isEqualTo(id);
        assertThat(customerDto.getFirstName()).isEqualTo(firstName);
        assertThat(customerDto.getMiddleName()).isEqualTo(middleName);
        assertThat(customerDto.getLastName()).isEqualTo(lastName);
        assertThat(customerDto.getEmail()).isEqualTo(email);
        assertThat(customerDto.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void testParameterizedConstructorWithNullMiddleName() {
        // Given
        UUID id = UUID.randomUUID();
        String firstName = "Jane";
        String lastName = "Smith";
        String email = "jane.smith@example.com";
        String phoneNumber = "555-5678";

        // When
        CustomerDto customerDto = new CustomerDto(id, firstName, null, lastName, email, phoneNumber);

        // Then
        assertThat(customerDto.getId()).isEqualTo(id);
        assertThat(customerDto.getFirstName()).isEqualTo(firstName);
        assertThat(customerDto.getMiddleName()).isNull();
        assertThat(customerDto.getLastName()).isEqualTo(lastName);
        assertThat(customerDto.getEmail()).isEqualTo(email);
        assertThat(customerDto.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void testParameterizedConstructorWithAllNullValues() {
        // When
        CustomerDto customerDto = new CustomerDto(null, null, null, null, null, null);

        // Then
        assertThat(customerDto.getId()).isNull();
        assertThat(customerDto.getFirstName()).isNull();
        assertThat(customerDto.getMiddleName()).isNull();
        assertThat(customerDto.getLastName()).isNull();
        assertThat(customerDto.getEmail()).isNull();
        assertThat(customerDto.getPhoneNumber()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        // Given
        CustomerDto customerDto = new CustomerDto();
        UUID id = UUID.randomUUID();
        String firstName = "Alice";
        String middleName = "Marie";
        String lastName = "Johnson";
        String email = "alice.johnson@example.com";
        String phoneNumber = "555-9999";

        // When
        customerDto.setId(id);
        customerDto.setFirstName(firstName);
        customerDto.setMiddleName(middleName);
        customerDto.setLastName(lastName);
        customerDto.setEmail(email);
        customerDto.setPhoneNumber(phoneNumber);

        // Then
        assertThat(customerDto.getId()).isEqualTo(id);
        assertThat(customerDto.getFirstName()).isEqualTo(firstName);
        assertThat(customerDto.getMiddleName()).isEqualTo(middleName);
        assertThat(customerDto.getLastName()).isEqualTo(lastName);
        assertThat(customerDto.getEmail()).isEqualTo(email);
        assertThat(customerDto.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void testSettersWithNullValues() {
        // Given
        CustomerDto customerDto = new CustomerDto();

        // When
        customerDto.setId(null);
        customerDto.setFirstName(null);
        customerDto.setMiddleName(null);
        customerDto.setLastName(null);
        customerDto.setEmail(null);
        customerDto.setPhoneNumber(null);

        // Then
        assertThat(customerDto.getId()).isNull();
        assertThat(customerDto.getFirstName()).isNull();
        assertThat(customerDto.getMiddleName()).isNull();
        assertThat(customerDto.getLastName()).isNull();
        assertThat(customerDto.getEmail()).isNull();
        assertThat(customerDto.getPhoneNumber()).isNull();
    }

    @Test
    void testSettersWithEmptyStrings() {
        // Given
        CustomerDto customerDto = new CustomerDto();

        // When
        customerDto.setFirstName("");
        customerDto.setMiddleName("");
        customerDto.setLastName("");
        customerDto.setEmail("");
        customerDto.setPhoneNumber("");

        // Then
        assertThat(customerDto.getFirstName()).isEmpty();
        assertThat(customerDto.getMiddleName()).isEmpty();
        assertThat(customerDto.getLastName()).isEmpty();
        assertThat(customerDto.getEmail()).isEmpty();
        assertThat(customerDto.getPhoneNumber()).isEmpty();
    }

    @Test
    void testFieldModification() {
        // Given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Original");
        customerDto.setEmail("original@example.com");

        // When
        customerDto.setFirstName("Modified");
        customerDto.setEmail("modified@example.com");

        // Then
        assertThat(customerDto.getFirstName()).isEqualTo("Modified");
        assertThat(customerDto.getEmail()).isEqualTo("modified@example.com");
    }

    @Test
    void testObjectStateConsistency() {
        // Given
        UUID id = UUID.randomUUID();
        CustomerDto customerDto1 = new CustomerDto(id, "John", "M", "Doe", "john@example.com", "555-1234");
        CustomerDto customerDto2 = new CustomerDto();

        // When
        customerDto2.setId(id);
        customerDto2.setFirstName("John");
        customerDto2.setMiddleName("M");
        customerDto2.setLastName("Doe");
        customerDto2.setEmail("john@example.com");
        customerDto2.setPhoneNumber("555-1234");

        // Then - Both objects should have the same field values
        assertThat(customerDto1.getId()).isEqualTo(customerDto2.getId());
        assertThat(customerDto1.getFirstName()).isEqualTo(customerDto2.getFirstName());
        assertThat(customerDto1.getMiddleName()).isEqualTo(customerDto2.getMiddleName());
        assertThat(customerDto1.getLastName()).isEqualTo(customerDto2.getLastName());
        assertThat(customerDto1.getEmail()).isEqualTo(customerDto2.getEmail());
        assertThat(customerDto1.getPhoneNumber()).isEqualTo(customerDto2.getPhoneNumber());
    }

    @Test
    void testConstructorParameterOrder() {
        // Given
        UUID id = UUID.randomUUID();

        // When
        CustomerDto customerDto = new CustomerDto(id, "First", "Middle", "Last", "email@test.com", "123-456");

        // Then - Verify constructor parameters are assigned to correct fields
        assertThat(customerDto.getId()).isEqualTo(id);
        assertThat(customerDto.getFirstName()).isEqualTo("First");
        assertThat(customerDto.getMiddleName()).isEqualTo("Middle");
        assertThat(customerDto.getLastName()).isEqualTo("Last");
        assertThat(customerDto.getEmail()).isEqualTo("email@test.com");
        assertThat(customerDto.getPhoneNumber()).isEqualTo("123-456");
    }

    @Test
    void testChainedSetters() {
        // Given
        CustomerDto customerDto = new CustomerDto();
        UUID id = UUID.randomUUID();
        customerDto.setId(id);
        customerDto.setFirstName("Bob");
        customerDto.setLastName("Wilson");
        customerDto.setEmail("bob.wilson@example.com");

        // Then
        assertThat(customerDto.getId()).isEqualTo(id);
        assertThat(customerDto.getFirstName()).isEqualTo("Bob");
        assertThat(customerDto.getLastName()).isEqualTo("Wilson");
        assertThat(customerDto.getEmail()).isEqualTo("bob.wilson@example.com");
    }

    @Test
    void testGetterReturnValues() {
        // Given
        UUID id = UUID.randomUUID();
        CustomerDto customerDto = new CustomerDto(id, "Test", "User", "Name", "test@example.com", "555-0000");

        // When & Then - Test that getters return exact same values
        assertThat(customerDto.getId()).isSameAs(id); // UUID should be same reference
        assertThat(customerDto.getFirstName()).isEqualTo("Test");
        assertThat(customerDto.getMiddleName()).isEqualTo("User");
        assertThat(customerDto.getLastName()).isEqualTo("Name");
        assertThat(customerDto.getEmail()).isEqualTo("test@example.com");
        assertThat(customerDto.getPhoneNumber()).isEqualTo("555-0000");
    }
}
