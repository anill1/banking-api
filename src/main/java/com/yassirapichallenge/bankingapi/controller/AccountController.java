package com.yassirapichallenge.bankingapi.controller;
import com.yassirapichallenge.bankingapi.dto.AccountDTO;
import com.yassirapichallenge.bankingapi.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create a new account", description = "Create a new account with customer ID and balance value and return created account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {

        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @Operation(summary = "Get balance value", description = "Get balance value for a given account with account ID and customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Page is Not found")
    })
    @GetMapping("/get-balance/{accountId}")
    public ResponseEntity<AccountDTO> getAccountBalance(@PathVariable Long accountId) {

        try {
            AccountDTO accountDTO = accountService.getAccountBalance(accountId);
            return ResponseEntity.ok(accountDTO);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}