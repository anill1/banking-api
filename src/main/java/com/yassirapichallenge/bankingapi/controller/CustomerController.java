package com.yassirapichallenge.bankingapi.controller;
import com.yassirapichallenge.bankingapi.dto.CustomerDTO;
import com.yassirapichallenge.bankingapi.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Create a new customer", description = "Returns the saved customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved"),
            @ApiResponse(responseCode = "404", description = "Saving new customer is unsuccessful"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {

        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }
}