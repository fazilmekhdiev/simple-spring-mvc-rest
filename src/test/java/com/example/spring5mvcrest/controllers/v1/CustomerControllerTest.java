package com.example.spring5mvcrest.controllers.v1;

import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Arrays;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends BaseControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .setControllerAdvice(new CustomerControllerAdvice())
                .build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("");
        customerDTO.setLastName("");
        customerDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstName("");
        customerDTO2.setLastName("");
        customerDTO2.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customerDTO, customerDTO2));

        mockMvc.perform(get(CustomerController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)))
                .andExpect(jsonPath("$.customers[0].firstName", is(customerDTO.getFirstName())))
                .andExpect(jsonPath("$.customers[0].lastName", is(customerDTO.getFirstName())))
                .andExpect(jsonPath("$.customers[0].customer_url", is(customerDTO.getCustomerUrl())));
    }

    @Test
    public void getCustomerById() throws Exception {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Michael");
        customerDTO.setLastName("Smith");

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get(CustomerController.BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(customerDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(customerDTO.getLastName())))
                .andExpect(jsonPath("$.customer_url", is(customerDTO.getCustomerUrl())));

        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    public void createCustomer() throws Exception {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Michael");
        customerDTO.setLastName("Smith");
        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(customerDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(customerDTO.getLastName())))
                .andExpect(jsonPath("$.customer_url", is(customerDTO.getCustomerUrl())));

        verify(customerService, times(1)).createCustomer(customerDTO);
    }


    @Test
    public void updateCustomer() throws Exception {

        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Michael");
        customerDTO.setLastName("Smith");

        when(customerService.updateCustomer(any(CustomerDTO.class), anyLong())).thenReturn(customerDTO);

        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(customerDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(customerDTO.getLastName())))
                .andExpect(jsonPath("$.customer_url", is(customerDTO.getCustomerUrl())));

        verify(customerService, times(1)).updateCustomer(customerDTO, 1L);

    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL + "/1"))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomerById(1L);
    }
}