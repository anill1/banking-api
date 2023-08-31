package com.yassirapichallenge.bankingapi.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account targetAccount;

    private double amount;

    private LocalDateTime timestamp;
}
