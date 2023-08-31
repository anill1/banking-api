package com.yassirapichallenge.bankingapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import com.yassirapichallenge.bankingapi.dto.CustomerDTO;
import com.yassirapichallenge.bankingapi.entity.Customer;
import com.yassirapichallenge.bankingapi.repository.CustomerRepository;
import com.yassirapichallenge.bankingapi.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CustomerServiceTest {

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private ModelMapper modelMapper;

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerService = new CustomerService(customerRepository, modelMapper);
    }

    @Test
    public void testCreateCustomer() {
        CustomerDTO inputCustomer = new CustomerDTO();
        inputCustomer.setName("Anil");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setName(inputCustomer.getName());
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        when(modelMapper.map(any(Customer.class), eq(CustomerDTO.class))).thenReturn(inputCustomer);

        CustomerDTO createdCustomer = customerService.createCustomer(inputCustomer);

        assertEquals(inputCustomer.getName(), createdCustomer.getName());
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;
        String customerName = "Anil";

        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);
        mockCustomer.setName(customerName);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        CustomerDTO mockCustomerDTO = new CustomerDTO();
        mockCustomerDTO.setId(customerId);
        mockCustomerDTO.setName(customerName);
        when(modelMapper.map(mockCustomer, CustomerDTO.class)).thenReturn(mockCustomerDTO);

        CustomerDTO retrievedCustomer = customerService.getCustomerById(customerId);

        assertEquals(customerName, retrievedCustomer.getName());
    }
}