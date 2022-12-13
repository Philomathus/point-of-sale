package com.ministore.pointofsale.model;

import com.ministore.pointofsale.annotations.ExcelColumn;
import lombok.Data;

@Data
public class Product {
    private Long id;

    @ExcelColumn(name="Product Name", order=0)
    private String name;

    @ExcelColumn(name="Unit", order=1)
    private String unit;

    @ExcelColumn(name="Unit Price", order=2)
    private Double unitPrice;

    @ExcelColumn(name="Quantity", order=3)
    private Double quantity;
}