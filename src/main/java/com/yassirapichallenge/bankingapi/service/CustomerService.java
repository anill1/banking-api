package com.yassirapichallenge.bankingapi.service;
import com.yassirapichallenge.bankingapi.dto.CustomerDTO;
import com.yassirapichallenge.bankingapi.entity.Customer;
import com.yassirapichallenge.bankingapi.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {

        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());

        Customer customerDB = customerRepository.save(customer);
        return modelMapper.map(customerDB, CustomerDTO.class);
    }

    public CustomerDTO getCustomerById(Long id) {

        Optional<Customer> customer = customerRepository.findById(id);

        if(customer.isPresent()){

            return modelMapper.map(customer.get(), CustomerDTO.class);
        }
        else
        {
            throw new EntityNotFoundException("Customer not found");
        }
    }

    public Optional<Customer> getCustomerEntityById(Long id) {
        return customerRepository.findById(id);
    }
}