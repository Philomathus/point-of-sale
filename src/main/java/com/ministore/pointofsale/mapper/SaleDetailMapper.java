package com.ministore.pointofsale.mapper;

import com.ministore.pointofsale.model.SaleDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SaleDetailMapper {

    List<SaleDetail> queryAll();

    List<SaleDetail> queryAllBySaleId(@Param("saleId") Long saleId);

    int insert(SaleDetail saleDetail);

    int batchInsert(@Param("saleDetailList") List<SaleDetail> saleDetailList);

    SaleDetail queryById(@Param("id") Long id);

    int updateById(SaleDetail saleDetail);

    int deleteById(@Param("id") Long id);

}
