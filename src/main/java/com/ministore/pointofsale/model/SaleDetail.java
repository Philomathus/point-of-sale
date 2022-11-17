package com.ministore.pointofsale.model;

import lombok.Data;

@Data
public class SaleDetail {
    private Long id;
    private Long productId;
    private Double quantity;
    private Double price;
    private Long saleId;
}
