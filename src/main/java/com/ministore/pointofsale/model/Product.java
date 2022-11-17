package com.ministore.pointofsale.model;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Double quantity;
    private String unit;
    private Double unitPrice;
}
