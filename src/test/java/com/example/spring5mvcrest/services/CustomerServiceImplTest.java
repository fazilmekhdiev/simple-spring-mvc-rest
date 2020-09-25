package com.example.spring5mvcrest.services;

import com.example.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.controllers.v1.CustomerController;
import com.example.spring5mvcrest.domain.Customer;
import com.example.spring5mvcrest.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerServiceImpl customerService;

    private CustomerMapper mapper = CustomerMapper.INSTANCE;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, mapper);
    }

    @Test
    public void getAllCustomers() {

        // given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Michael");
        customer1.setLastName("Smith");

        Customer customer2 = new Customer();
        customer2.setFirstName("Chad");
        customer2.setLastName("Darby");
        customer2.setId(2L);

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        // when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        // then
        assertThat(customerDTOS, hasSize(2));
        verify(customerRepository, times(1)).findAll();

    }

    @Test
    public void getCustomerById() {

        // given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Michael");
        customer1.setLastName("Smith");

        Optional<Customer> customerOptional = Optional.of(customer1);

        when(customerRepository.findById(anyLong())).thenReturn(customerOptional);

        // when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        // then
        assertEquals(customer1.getFirstName(), customerDTO.getFirstName());
        assertEquals(customer1.getLastName(), customerDTO.getLastName());
        verify(customerRepository, times(1)).findById(1L);

    }


    @Test
    public void createCustomer() {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Micahel");
        customerDTO.setLastName("Smith");

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Michael");
        customer1.setLastName("Smith");


        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        // when
        CustomerDTO savedDto = customerService.createCustomer(customerDTO);

        // then
        assertEquals(customer1.getFirstName(), savedDto.getFirstName());
        assertEquals(customer1.getLastName(), savedDto.getLastName());
        assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());

        verify(customerRepository, times(1)).save(any(Customer.class));


    }

    @Test
    public void updateCustomer() {

        // given

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Michael");
        customerDTO.setLastName("Smith");

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Michael");
        customer1.setLastName("Smith");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);


        CustomerDTO updatedDto = customerService.updateCustomer(customerDTO, 1L);

        assertEquals(customer1.getFirstName(), customerDTO.getFirstName());
        assertEquals(customer1.getLastName(), customerDTO.getLastName());
        assertEquals(CustomerController.BASE_URL + "/1", updatedDto.getCustomerUrl());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void deleteCustomerById() {

        customerService.deleteCustomerById(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }


}











