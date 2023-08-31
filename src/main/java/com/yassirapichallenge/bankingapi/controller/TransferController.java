package com.yassirapichallenge.bankingapi.controller;
import com.yassirapichallenge.bankingapi.dto.TransferDTO;
import com.yassirapichallenge.bankingapi.exceptionhandler.InsufficientBalanceException;
import com.yassirapichallenge.bankingapi.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @Operation(summary = "Create Transfer", description = "Create transfer for given accounts and return created transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/create")
    public ResponseEntity<TransferDTO> createTransfer(@RequestBody TransferDTO transferDTO) throws InsufficientBalanceException {

        TransferDTO createdTransfer = transferService.createTransfer(transferDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransfer);
    }

    @Operation(summary = "Get transfer history", description = "Get transfer history for a given account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Page is Not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("/get-transfer-history/{accountId}")
    public ResponseEntity<List<TransferDTO>> getTransferHistoryForAccount(@PathVariable Long accountId) {

        List<TransferDTO> transfers = transferService.getTransferHistoryForAccount(accountId);
        if (transfers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transfers);
    }
}