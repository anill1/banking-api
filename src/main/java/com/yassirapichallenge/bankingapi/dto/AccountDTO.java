package com.yassirapichallenge.bankingapi.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
@Data
public class AccountDTO implements Serializable {

    private Long id;

    @NotNull(message = "Customer is required")
    private Long customerId;

    private Double balance;
}