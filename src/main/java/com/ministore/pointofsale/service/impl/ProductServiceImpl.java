package com.ministore.pointofsale.service.impl;

import com.ministore.pointofsale.exception.ServiceException;
import com.ministore.pointofsale.mapper.ProductMapper;
import com.ministore.pointofsale.model.Product;
import com.ministore.pointofsale.service.ProductService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public void add(Product product) {

        if(product == null) {
            throw new ServiceException(500, "The product cannot be null!");
        }

        if(Strings.isBlank(product.getName())) {
            throw new ServiceException(500, "The product name cannot be blank!");
        }

        if(product.getQuantity() == null || product.getQuantity() < 0) {
            throw new ServiceException(500, "A non-negative quantity must be provided!");
        }

        if(Strings.isBlank(product.getUnit())) {
            throw new ServiceException(500, "A valid unit must be provided!");
        }

        if(product.getUnitPrice() == null || product.getUnitPrice() < 0) {
            throw new ServiceException(500, "A non-negative unit-price must be provided!");
        }

        if(productMapper.queryByName(product.getName()) != null) {
            throw new ServiceException(500, "The name already exists!");
        }

        productMapper.insert(product);
    }

    @Override
    public void update(Product product) {
        productMapper.updateById(product);
    }

    @Override
    public List<Product> getAll() {
        return productMapper.queryAll();
    }

    @Override
    public Product getById(Long id) {

        if(id == null) {
            throw new ServiceException(500, "The id cannot be null!");
        }

        return productMapper.queryById(id);
    }

    @Override
    public void delete(Long id) {

        if(getById(id) == null) {
            throw new ServiceException(500, "The product does not exist!");
        }

        productMapper.deleteById(id);

    }

    @Override
    public void adjustQuantityById(Long id, Double changeInQuantity) {
        if(id == null) {
            throw new ServiceException(500, "The id must not be null!");
        }

        if(changeInQuantity == null) {
            throw new ServiceException(500, "The quantity must not be null!");
        }

        Product product = productMapper.queryById(id);

        if(product == null) {
            throw new ServiceException(500, "The product does not exist!");
        }

        product.setQuantity(product.getQuantity() + changeInQuantity);

        if(product.getQuantity() < 0) {
            throw new ServiceException(500, "Insufficient quantity!");
        }

        productMapper.updateById(product);
    }
}
