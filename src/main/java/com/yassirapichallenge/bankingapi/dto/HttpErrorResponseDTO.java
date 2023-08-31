package com.yassirapichallenge.bankingapi.dto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HttpErrorResponseDTO {

    private String message;

    private Integer httpCode;
}
