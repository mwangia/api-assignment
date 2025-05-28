package com.interview.assignment.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerEntityTest {

    @Test
    void testDefaultConstructor() {
        // When
        CustomerEntity customer = new CustomerEntity();

        // Then
        assertThat(customer.getId()).isNull();
        assertThat(customer.getFirstName()).isNull();
        assertThat(customer.getMiddleName()).isNull();
        assertThat(customer.getLastName()).isNull();
        assertThat(customer.getEmail()).isNull();
        assertThat(customer.getPhoneNumber()).isNull();
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
        CustomerEntity customer = new CustomerEntity(id, firstName, middleName, lastName, email, phoneNumber);

        // Then
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getFirstName()).isEqualTo(firstName);
        assertThat(customer.getMiddleName()).isEqualTo(middleName);
        assertThat(customer.getLastName()).isEqualTo(lastName);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getPhoneNumber()).isEqualTo(phoneNumber);
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
        CustomerEntity customer = new CustomerEntity(id, firstName, null, lastName, email, phoneNumber);

        // Then
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getFirstName()).isEqualTo(firstName);
        assertThat(customer.getMiddleName()).isNull();
        assertThat(customer.getLastName()).isEqualTo(lastName);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        CustomerEntity customer = new CustomerEntity();
        UUID id = UUID.randomUUID();
        String firstName = "Alice";
        String middleName = "Marie";
        String lastName = "Johnson";
        String email = "alice.johnson@example.com";
        String phoneNumber = "555-9999";

        // When
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setMiddleName(middleName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);

        // Then
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getFirstName()).isEqualTo(firstName);
        assertThat(customer.getMiddleName()).isEqualTo(middleName);
        assertThat(customer.getLastName()).isEqualTo(lastName);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void testSettersWithNullValues() {
        // Given
        CustomerEntity customer = new CustomerEntity();

        // When
        customer.setId(null);
        customer.setFirstName(null);
        customer.setMiddleName(null);
        customer.setLastName(null);
        customer.setEmail(null);
        customer.setPhoneNumber(null);

        // Then
        assertThat(customer.getId()).isNull();
        assertThat(customer.getFirstName()).isNull();
        assertThat(customer.getMiddleName()).isNull();
        assertThat(customer.getLastName()).isNull();
        assertThat(customer.getEmail()).isNull();
        assertThat(customer.getPhoneNumber()).isNull();
    }

    @Test
    void testSettersWithEmptyStrings() {
        // Given
        CustomerEntity customer = new CustomerEntity();

        // When
        customer.setFirstName("");
        customer.setMiddleName("");
        customer.setLastName("");
        customer.setEmail("");
        customer.setPhoneNumber("");

        // Then
        assertThat(customer.getFirstName()).isEmpty();
        assertThat(customer.getMiddleName()).isEmpty();
        assertThat(customer.getLastName()).isEmpty();
        assertThat(customer.getEmail()).isEmpty();
        assertThat(customer.getPhoneNumber()).isEmpty();
    }

    @Test
    void testFieldModification() {
        // Given
        CustomerEntity customer = new CustomerEntity();
        customer.setFirstName("Original");
        customer.setEmail("original@example.com");

        // When
        customer.setFirstName("Modified");
        customer.setEmail("modified@example.com");

        // Then
        assertThat(customer.getFirstName()).isEqualTo("Modified");
        assertThat(customer.getEmail()).isEqualTo("modified@example.com");
    }

    @Test
    void testObjectStateConsistency() {
        // Given
        UUID id = UUID.randomUUID();
        CustomerEntity customer1 = new CustomerEntity(id, "John", "M", "Doe", "john@example.com", "555-1234");
        CustomerEntity customer2 = new CustomerEntity();

        // When
        customer2.setId(id);
        customer2.setFirstName("John");
        customer2.setMiddleName("M");
        customer2.setLastName("Doe");
        customer2.setEmail("john@example.com");
        customer2.setPhoneNumber("555-1234");

        // Then - Both objects should have the same field values
        assertThat(customer1.getId()).isEqualTo(customer2.getId());
        assertThat(customer1.getFirstName()).isEqualTo(customer2.getFirstName());
        assertThat(customer1.getMiddleName()).isEqualTo(customer2.getMiddleName());
        assertThat(customer1.getLastName()).isEqualTo(customer2.getLastName());
        assertThat(customer1.getEmail()).isEqualTo(customer2.getEmail());
        assertThat(customer1.getPhoneNumber()).isEqualTo(customer2.getPhoneNumber());
    }

    @Test
    void testConstructorParameterOrder() {
        // Given
        UUID id = UUID.randomUUID();

        // When
        CustomerEntity customer = new CustomerEntity(id, "First", "Middle", "Last", "email@test.com", "123-456");

        // Then - Verify constructor parameters are assigned to correct fields
        assertThat(customer.getId()).isEqualTo(id);
        assertThat(customer.getFirstName()).isEqualTo("First");
        assertThat(customer.getMiddleName()).isEqualTo("Middle");
        assertThat(customer.getLastName()).isEqualTo("Last");
        assertThat(customer.getEmail()).isEqualTo("email@test.com");
        assertThat(customer.getPhoneNumber()).isEqualTo("123-456");
    }
}
