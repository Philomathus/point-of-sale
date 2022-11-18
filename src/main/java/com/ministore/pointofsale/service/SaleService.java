package com.ministore.pointofsale.service;

import com.ministore.pointofsale.dto.SaleDto;
import com.ministore.pointofsale.model.Sale;

import java.util.List;

public interface SaleService {

    List<Sale> getAll();

    List<SaleDto> getAllWithDetails();

    Sale getById(Long id);

    SaleDto getByIdWithDetails(Long id);

    void addSaleWithDetails(SaleDto saleDto);

}
