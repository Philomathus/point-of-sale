package com.ministore.pointofsale.model;

import lombok.Data;

import java.util.Objects;

@Data
public class SaleDetail {
    private Long id;
    private Long productId;
    private Double quantity;
    private Double price;
    private Long saleId;

    public boolean equals(Object obj) {

        if(!(obj instanceof SaleDetail)) {
            return false;
        }

        SaleDetail that = (SaleDetail) obj;

        return Objects.equals(productId, that.productId) && Objects.equals(saleId, that.saleId);

    }

    public int hashCode() {
        return Objects.hash(productId, saleId);
    }
}
