package com.interview.apiassignment.consumer;

import com.interview.apiassignment.consumer.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws Exception{
        String url;
        if (args.length > 0) {
            url = args[0];
            log.info("Using URL: " + url);
        } else {
            log.error("No URL provided. The endpoint URL is required");
            log.error("Usage: ./gradlew :api-consumer:run --args=\"<url>\"");
            return;
        }

        CustomerClient client = new CustomerClient(url);

        CustomerDto newCustomer = new CustomerDto(null,"John", "Jay", "Smith", "john1@smith.com", "123-456-7890");
        client.createCustomer(newCustomer);

        List<CustomerDto> customerDtoList = client.listCustomers();

        // update customer and get updated customer
        if(!customerDtoList.isEmpty()) {
            CustomerDto customerDto = customerDtoList.get(0);
            customerDto.setLastName("Jaye");
            client.updateCustomer(customerDto);
            Optional<CustomerDto> updatedCustomerOptional = client.getCustomer(customerDto.getId());
            updatedCustomerOptional.ifPresent(dto -> log.info("Updated customer: " + dto));
        }

        // patch update customer
        if(!customerDtoList.isEmpty()) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customerDtoList.get(0).getId());
            customerDto.setPhoneNumber("444-444-4444");
            client.patchUpdateCustomer(customerDto);
        }

        //delete customer
        if (!customerDtoList.isEmpty()) {
            client.deleteCustomer(customerDtoList.get(0).getId());
            client.listCustomers();
        }

    }
}