package com.yassirapichallenge.bankingapi;
import com.yassirapichallenge.bankingapi.dto.AccountDTO;
import com.yassirapichallenge.bankingapi.entity.Account;
import com.yassirapichallenge.bankingapi.entity.Customer;
import com.yassirapichallenge.bankingapi.repository.AccountRepository;
import com.yassirapichallenge.bankingapi.service.AccountService;
import com.yassirapichallenge.bankingapi.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAccount_Success() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCustomerId(1L);
        accountDTO.setBalance(100.0);

        Customer customer = new Customer();
        when(customerService.getCustomerEntityById(1L)).thenReturn(Optional.of(customer));

        Account savedAccount = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        AccountDTO resultDTO = new AccountDTO();
        when(modelMapper.map(savedAccount, AccountDTO.class)).thenReturn(resultDTO);

        AccountDTO createdAccount = accountService.createAccount(accountDTO);

        assertNotNull(createdAccount);
        assertEquals(resultDTO, createdAccount);
    }

    @Test
    public void testCreateAccount_CustomerNotFound() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCustomerId(1L);

        when(customerService.getCustomerEntityById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.createAccount(accountDTO));
    }

    @Test
    public void testGetAccountBalance_Success() {
        Account account = new Account();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        AccountDTO resultDTO = new AccountDTO();
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(resultDTO);

        AccountDTO accountBalance = accountService.getAccountBalance(1L);

        assertNotNull(accountBalance);
        assertEquals(resultDTO, accountBalance);
    }

    @Test
    public void testGetAccountBalance_AccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.getAccountBalance(1L));
    }
}