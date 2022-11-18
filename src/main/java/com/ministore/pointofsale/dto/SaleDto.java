package com.ministore.pointofsale.dto;

import com.ministore.pointofsale.model.Sale;
import com.ministore.pointofsale.model.SaleDetail;
import lombok.Data;

import java.util.List;

@Data
public class SaleDto extends Sale {

    private List<SaleDetail> saleDetails;

}