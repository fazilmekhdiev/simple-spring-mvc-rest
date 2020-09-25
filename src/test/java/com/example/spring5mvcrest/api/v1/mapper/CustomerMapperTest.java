package com.example.spring5mvcrest.api.v1.mapper;

import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.domain.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CustomerMapperTest {


    public static final long ID = 1L;
    public static final String NAME = "Michael";
    public static final String LAST_NAME = "Smith";

    CustomerMapper customerMapper;

    @Before
    public void setUp() throws Exception {
        customerMapper = CustomerMapper.INSTANCE;
    }

    @Test
    public void customerToCustomerDTO() {
        // given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(NAME);
        customer.setLastName(LAST_NAME);

        // when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        // then
        assertThat(customerDTO.getFirstName(), is(equalTo(NAME)));
        assertThat(customerDTO.getLastName(), is(equalTo(LAST_NAME)));


    }

    @Test
    public void customerDTOtoCustomer() {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(NAME);
        customerDTO.setLastName(LAST_NAME);

        // when
        Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);

        assertThat(customer.getFirstName(), is(equalTo(NAME)));
        assertThat(customer.getLastName(), is(equalTo(LAST_NAME)));

    }
}