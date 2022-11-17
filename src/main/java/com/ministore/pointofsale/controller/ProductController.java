package com.ministore.pointofsale.controller;

import com.ministore.pointofsale.dto.ProductDto;
import com.ministore.pointofsale.exception.ServiceException;
import com.ministore.pointofsale.model.Product;
import com.ministore.pointofsale.service.ProductService;
import com.ministore.pointofsale.vo.ResponseHelper;
import com.ministore.pointofsale.vo.ResponseVO;
import com.ministore.pointofsale.vo.ServiceStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseVO<ServiceStatusCode> addProduct(@RequestBody Product product) {

        try {
            productService.add(product);
            return ResponseHelper.success();
        } catch(ServiceException ex) {
            return new ResponseVO<>(ex.getCode(), ex.getMessage());
        } catch(Exception ex) {
            return ResponseHelper.error();
        }

    }

    @PostMapping("/getAll")
    public ResponseVO<List<Product>> getALl() {

        try {
            return ResponseHelper.success(productService.getAll());
        } catch(Exception ex) {
            return ResponseHelper.error();
        }

    }

    @PostMapping("/adjustQuantityById")
    public ResponseVO<ServiceStatusCode> adjustQuantityById(@RequestBody ProductDto productDto) {

        try {
            productService.adjustQuantityById(productDto.getId(), productDto.getChangeInQuantity());
            return ResponseHelper.success();
        } catch(Exception ex) {
            return ResponseHelper.error();
        }

    }

}
