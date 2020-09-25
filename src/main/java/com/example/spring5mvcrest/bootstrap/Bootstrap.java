package com.example.spring5mvcrest.bootstrap;

import com.example.spring5mvcrest.domain.Customer;
import com.example.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Bootstrap implements CommandLineRunner {

    private CustomerRepository customerRepository;

    public Bootstrap(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // load customers to database during application start
        loadCustomers();

    }

    private void loadCustomers() {
        // declare customers
        Customer customer1 = new Customer();
        customer1.setFirstName("Michael");
        customer1.setLastName("Smith");

        Customer customer2 = new Customer();
        customer2.setFirstName("Chad");
        customer2.setLastName("Darby");

        Customer customer3 = new Customer();
        customer3.setFirstName("John");
        customer3.setLastName("Thomson");

        // save customers to database
        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
    }

}