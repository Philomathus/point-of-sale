package com.ministore.pointofsale.mapper;

import com.ministore.pointofsale.model.Sale;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SaleMapper {

    List<Sale> queryAll();

    int insert(Sale sale);

    Sale queryById(@Param("id") Long id);

    int updateById(Sale sale);

    int deleteById(@Param("id") Long id);

}
