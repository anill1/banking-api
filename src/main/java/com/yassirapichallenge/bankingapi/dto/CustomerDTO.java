package com.yassirapichallenge.bankingapi.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
@Data
public class CustomerDTO implements Serializable {
    private Long id;
    @Size(max = 100, message = "Name can't be longer than 100 characters")
    @NotEmpty(message = "Name is required")
    private String name;
}