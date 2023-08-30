package com.yassirapichallenge.bankingapi;

import com.yassirapichallenge.bankingapi.dto.TransferDTO;
import com.yassirapichallenge.bankingapi.entity.Account;
import com.yassirapichallenge.bankingapi.entity.Transfer;
import com.yassirapichallenge.bankingapi.exceptionhandler.InsufficientBalanceException;
import com.yassirapichallenge.bankingapi.repository.TransferRepository;
import com.yassirapichallenge.bankingapi.service.AccountService;
import com.yassirapichallenge.bankingapi.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransferServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TransferService transferService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTransfer_Success() throws InsufficientBalanceException {
        Account sourceAccount = new Account();
        sourceAccount.setBalance(1000.0);

        Account targetAccount = new Account();
        targetAccount.setBalance(500.0);

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSourceAccountId(1L);
        transferDTO.setTargetAccountId(2L);
        transferDTO.setAmount(300.0);

        Transfer createdTransfer = new Transfer();
        createdTransfer.setSourceAccount(sourceAccount);
        createdTransfer.setTargetAccount(targetAccount);
        createdTransfer.setAmount(300.0);
        createdTransfer.setTimestamp(LocalDateTime.now());

        when(accountService.getAccountById(1L)).thenReturn(sourceAccount);
        when(accountService.getAccountById(2L)).thenReturn(targetAccount);
        when(transferRepository.save(any(Transfer.class))).thenReturn(createdTransfer);

        TransferDTO resultDTO = new TransferDTO();
        when(modelMapper.map(createdTransfer, TransferDTO.class)).thenReturn(resultDTO);

        TransferDTO createdTransferDTO = transferService.createTransfer(transferDTO);

        assertNotNull(createdTransferDTO);
        assertEquals(resultDTO, createdTransferDTO);
    }

    @Test
    public void testGetTransferHistoryForAccount_Success() {
        Transfer transfer1 = new Transfer();
        Transfer transfer2 = new Transfer();

        List<Transfer> transfers = new ArrayList<>();
        transfers.add(transfer1);
        transfers.add(transfer2);

        when(transferRepository.findBySourceAccountIdOrTargetAccountId(1L, 1L)).thenReturn(Optional.of(transfers));

        TransferDTO transferDTO1 = new TransferDTO();
        TransferDTO transferDTO2 = new TransferDTO();

        when(modelMapper.map(transfer1, TransferDTO.class)).thenReturn(transferDTO1);
        when(modelMapper.map(transfer2, TransferDTO.class)).thenReturn(transferDTO2);

        List<TransferDTO> transferDTOs = transferService.getTransferHistoryForAccount(1L);

        assertNotNull(transferDTOs);
        assertEquals(2, transferDTOs.size());
    }

    @Test
    public void testGetTransferHistoryForAccount_NoTransfers() {
        when(transferRepository.findBySourceAccountIdOrTargetAccountId(1L, 1L)).thenReturn(Optional.empty());

        List<TransferDTO> transferDTOs = transferService.getTransferHistoryForAccount(1L);

        assertNotNull(transferDTOs);
        assertTrue(transferDTOs.isEmpty());
    }

}
