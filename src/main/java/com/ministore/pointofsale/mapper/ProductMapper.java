package com.ministore.pointofsale.mapper;

import com.ministore.pointofsale.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> queryAll();

    int insert(Product product);

    Product queryById(@Param("id") Long id);

    int updateById(Product product);

    int adjustQuantityById(@Param("id") Long id, @Param("changeInQuantity") Double changeInQuantity);

    int deleteById(@Param("id") Long id);

}
