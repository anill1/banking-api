package com.yassirapichallenge.bankingapi.service;
import com.yassirapichallenge.bankingapi.dto.TransferDTO;
import com.yassirapichallenge.bankingapi.entity.Account;
import com.yassirapichallenge.bankingapi.entity.Transfer;
import com.yassirapichallenge.bankingapi.exceptionhandler.InsufficientBalanceException;
import com.yassirapichallenge.bankingapi.repository.TransferRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class TransferService {
    private final AccountService accountService;
    private final TransferRepository transferRepository;
    private final ModelMapper modelMapper;
    public TransferService(AccountService accountService, TransferRepository transferRepository, ModelMapper modelMapper) {
        this.accountService = accountService;
        this.transferRepository = transferRepository;
        this.modelMapper = modelMapper;
    }
    @Transactional
    public TransferDTO createTransfer(TransferDTO transferDTO) throws InsufficientBalanceException {
        Account sourceAccount = accountService.getAccountById(transferDTO.getSourceAccountId());
        Account targetAccount = accountService.getAccountById(transferDTO.getTargetAccountId());

        if (sourceAccount.getBalance() >= transferDTO.getAmount()) {
            double amount = transferDTO.getAmount();
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
            targetAccount.setBalance(targetAccount.getBalance() + amount);

            accountService.saveAccount(sourceAccount);
            accountService.saveAccount(targetAccount);

            Transfer transfer = new Transfer();
            transfer.setSourceAccount(sourceAccount);
            transfer.setTargetAccount(targetAccount);
            transfer.setAmount(amount);
            transfer.setTimestamp(LocalDateTime.now());

            Transfer createdTransfer = transferRepository.save(transfer);
            return modelMapper.map(createdTransfer,TransferDTO.class);
        }
        else {
            throw new InsufficientBalanceException("Insufficient balance in the source account.");
        }
    }
    public TransferDTO convertToTransferDTO(Transfer transfer) {
        return modelMapper.map(transfer, TransferDTO.class);
    }
    public List<TransferDTO> getTransferHistoryForAccount(Long accountId) {
        Optional<List<Transfer>> optionalTransfer = transferRepository.findBySourceAccountIdOrTargetAccountId(accountId, accountId);

        if (optionalTransfer.isPresent()) {
            return optionalTransfer.get().stream()
                    .map(this::convertToTransferDTO)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}