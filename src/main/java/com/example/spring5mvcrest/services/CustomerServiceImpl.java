package com.example.spring5mvcrest.services;


import com.example.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.example.spring5mvcrest.api.v1.model.CustomerDTO;
import com.example.spring5mvcrest.controllers.v1.CustomerController;
import com.example.spring5mvcrest.domain.Customer;
import com.example.spring5mvcrest.exception.NotFoundException;
import com.example.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream().map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + customer.getId());
                    return customerDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .map(customerDTO -> {
                    customerDTO.setCustomerUrl(getCustomerUrl(id));
                    return customerDTO;
                }).orElseThrow(() -> {throw new NotFoundException(id);});
    }


    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDto(customerMapper.customerDTOtoCustomer(customerDTO));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id) {
        Customer customer = customerMapper.customerDTOtoCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDto(customer);
    }

    private CustomerDTO saveAndReturnDto(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);
        returnDto.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
        return returnDto;
    }

    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL + "/" + id;
    }
}