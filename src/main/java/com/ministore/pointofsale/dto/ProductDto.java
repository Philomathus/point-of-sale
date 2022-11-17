package com.ministore.pointofsale.dto;

import com.ministore.pointofsale.model.Product;
import lombok.Data;

@Data
public class ProductDto extends Product {
    private Double changeInQuantity;
}
