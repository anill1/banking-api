package com.yassirapichallenge.bankingapi.service;
import com.yassirapichallenge.bankingapi.dto.AccountDTO;
import com.yassirapichallenge.bankingapi.entity.Account;
import com.yassirapichallenge.bankingapi.entity.Customer;
import com.yassirapichallenge.bankingapi.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Optional<Customer> customerOptional = customerService.getCustomerEntityById(accountDTO.getCustomerId());

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            Account account = new Account();
            account.setCustomer(customer);
            Account accountDB = accountRepository.save(account);
            return modelMapper.map(accountDB, AccountDTO.class);
        } else {
            throw new EntityNotFoundException("Customer not found with id: " + accountDTO.getCustomerId());
        }
    }
    public AccountDTO getAccountBalance(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));

        return modelMapper.map(account, AccountDTO.class);
    }

    public Account getAccountById(Long id) {

        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));

    }
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

}