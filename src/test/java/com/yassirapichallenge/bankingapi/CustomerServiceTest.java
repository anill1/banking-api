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
        // Girdi verilerini oluştur
        CustomerDTO inputCustomer = new CustomerDTO();
        inputCustomer.setName("John Doe");

        // Simüle edilmiş repository davranışı
        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setName(inputCustomer.getName());
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // Simüle edilmiş ModelMapper davranışı
        when(modelMapper.map(any(Customer.class), eq(CustomerDTO.class))).thenReturn(inputCustomer);

        // Hizmeti çağır
        CustomerDTO createdCustomer = customerService.createCustomer(inputCustomer);

        // Testleri yap
        assertEquals(inputCustomer.getName(), createdCustomer.getName());
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;
        String customerName = "John Doe";

        // Simüle edilmiş repository davranışı
        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);
        mockCustomer.setName(customerName);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        // Simüle edilmiş ModelMapper davranışı
        CustomerDTO mockCustomerDTO = new CustomerDTO();
        mockCustomerDTO.setId(customerId);
        mockCustomerDTO.setName(customerName);
        when(modelMapper.map(mockCustomer, CustomerDTO.class)).thenReturn(mockCustomerDTO);

        // Hizmeti çağır
        CustomerDTO retrievedCustomer = customerService.getCustomerById(customerId);

        // Testleri yap
        assertEquals(customerName, retrievedCustomer.getName());
    }
}