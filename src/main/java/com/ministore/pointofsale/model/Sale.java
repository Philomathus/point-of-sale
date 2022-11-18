package com.ministore.pointofsale.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sale {
    private Long id;
    private LocalDateTime dateOfPurchase;
    private Double totalPrice;
    private Double cashReceived;
    private Double change;
}
