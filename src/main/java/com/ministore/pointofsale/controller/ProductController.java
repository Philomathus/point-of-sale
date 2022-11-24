package com.ministore.pointofsale.controller;

import com.ministore.pointofsale.dto.ProductDto;
import com.ministore.pointofsale.model.Product;
import com.ministore.pointofsale.service.iface.ProductService;
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
        productService.add(product);
        return ResponseHelper.success();
    }

    @PostMapping("/getAll")
    public ResponseVO<List<Product>> getAll() {
        return ResponseHelper.success(productService.getAll());
    }

    @PostMapping("/adjustQuantityById")
    public ResponseVO<ServiceStatusCode> adjustQuantityById(@RequestBody ProductDto productDto) {
        productService.adjustQuantityById(productDto.getId(), productDto.getChangeInQuantity());
        return ResponseHelper.success();
    }

}
