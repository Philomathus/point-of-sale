package com.ministore.pointofsale.service.iface;

import com.ministore.pointofsale.model.Product;

import java.util.List;

public interface ProductService {

    void add(Product product);

    void update(Product product);

    List<Product> getAll();

    Product getById(Long id);

    void delete(Long id);

    void adjustQuantityById(Long id, Double changeInQuantity);

}
