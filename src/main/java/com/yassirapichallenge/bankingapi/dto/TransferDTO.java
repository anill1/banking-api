package com.yassirapichallenge.bankingapi.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serializable;
@Data
public class TransferDTO implements Serializable {
    @NotNull
    private Long sourceAccountId;
    @NotNull
    private Long targetAccountId;
    @NotNull(message = "Amount is required")
    private Double amount;
}