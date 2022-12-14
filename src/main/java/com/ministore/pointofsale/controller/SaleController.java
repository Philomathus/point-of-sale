package com.ministore.pointofsale.controller;

import com.ministore.pointofsale.dto.SaleDto;
import com.ministore.pointofsale.exception.ServiceException;
import com.ministore.pointofsale.model.Product;
import com.ministore.pointofsale.model.Sale;
import com.ministore.pointofsale.model.SaleDetail;
import com.ministore.pointofsale.service.ProductService;
import com.ministore.pointofsale.service.SaleService;
import com.ministore.pointofsale.vo.ResponseHelper;
import com.ministore.pointofsale.vo.ResponseVO;
import com.ministore.pointofsale.vo.ServiceStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleService saleService;

    @PostMapping("/getAll")
    public ResponseVO<List<Sale>> getAll() {
        return ResponseHelper.success(saleService.getAll());
    }

    @PostMapping("/getAllWithDetails")
    public ResponseVO<List<SaleDto>> getAllWithDetails() {

        return ResponseHelper.success(saleService.getAllWithDetails());

    }

    @PostMapping("/addSaleWithDetails")
    @Transactional
    public ResponseVO<ServiceStatusCode> addSaleWithDetails(@RequestBody SaleDto saleDto) {

        saleService.addSaleWithDetails(saleDto);

        for(SaleDetail saleDetail : saleDto.getSaleDetails()) {
            productService.adjustQuantityById(saleDetail.getProductId(), -saleDetail.getQuantity());
        }

        return ResponseHelper.success();
    }

}