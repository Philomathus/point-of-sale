package com.ministore.pointofsale.service.impl;

import com.ministore.pointofsale.dto.SaleDto;
import com.ministore.pointofsale.exception.ServiceException;
import com.ministore.pointofsale.mapper.ProductMapper;
import com.ministore.pointofsale.mapper.SaleDetailMapper;
import com.ministore.pointofsale.mapper.SaleMapper;
import com.ministore.pointofsale.model.Product;
import com.ministore.pointofsale.model.Sale;
import com.ministore.pointofsale.model.SaleDetail;
import com.ministore.pointofsale.service.iface.ProductService;
import com.ministore.pointofsale.service.iface.SaleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private SaleDetailMapper saleDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @Override
    public List<Sale> getAll() {
        return saleMapper.queryAll();
    }

    @Override
    public List<SaleDto> getAllWithDetails() {
        List<SaleDto> saleDtoList = new LinkedList<>();
        for(Sale sale : saleMapper.queryAll()) {
            SaleDto saleDto = new SaleDto();
            BeanUtils.copyProperties(sale, saleDto);
            saleDto.setSaleDetails(saleDetailMapper.queryAllBySaleId(sale.getId()));
            saleDtoList.add(saleDto);
        }

        return saleDtoList;
    }

    @Override
    public Sale getById(Long id) {
        if(id == null) {
            throw new ServiceException(500, "The id must not be null!");
        }

        return saleMapper.queryById(id);
    }

    @Override
    public SaleDto getByIdWithDetails(Long id) {
        if(id == null) {
            throw new ServiceException(500, "The id must not be null!");
        }

        Sale sale = saleMapper.queryById(id);

        if(sale == null) {
            return null;
        }

        SaleDto saleDto = new SaleDto();
        BeanUtils.copyProperties(sale, saleDto);
        saleDto.setSaleDetails(saleDetailMapper.queryAllBySaleId(id));

        return saleDto;
    }

    @Override
    @Transactional
    public void makeSaleWithDetails(SaleDto saleDto) {
        addSaleWithDetails(saleDto);

        for(SaleDetail saleDetail : saleDto.getSaleDetails()) {
            productService.adjustQuantityById(saleDetail.getProductId(), -saleDetail.getQuantity());
        }
    }

    private void addSaleWithDetails(SaleDto saleDto) {

        if(saleDto == null) {
            throw new ServiceException(500, "The saleDto cannot be null!");
        }

        if(saleDto.getSaleDetails() == null || saleDto.getSaleDetails().size() == 0) {
            throw new ServiceException(500, "The sale detail list cannot be empty!");
        }

        Set<SaleDetail> saleDetailSet = new HashSet<>(saleDto.getSaleDetails());
        if(saleDetailSet.size() != saleDto.getSaleDetails().size()) {
            throw new ServiceException(500, "The sale detail list cannot have duplicates!");
        }

        if(saleDto.getCashReceived() <= 0) {
            throw new ServiceException(500, "The cash received must be positive!");
        }

        double totalPrice = 0;
        for(SaleDetail saleDetail : saleDto.getSaleDetails()) {
            Product product = productMapper.queryById(saleDetail.getProductId());

            if(product == null) {
                throw new ServiceException(500, "The product does not exist!");
            }

            if(saleDetail.getQuantity() <= 0) {
                throw new ServiceException(500, "The quantity must be positive!");
            }

            saleDetail.setPrice(saleDetail.getQuantity() * product.getUnitPrice());
            totalPrice += saleDetail.getPrice();
        }

        saleDto.setTotalPrice(totalPrice);
        saleDto.setChange(saleDto.getCashReceived() - totalPrice);

        if(saleDto.getChange() < 0) {
            throw new ServiceException(500, "Insufficient cash!");
        }

        saleDto.setDateOfPurchase(LocalDateTime.now());

        saleMapper.insert(saleDto);

        for(SaleDetail saleDetail : saleDto.getSaleDetails()) {
            saleDetail.setSaleId(saleDto.getId());
        }

        saleDetailMapper.batchInsert(saleDto.getSaleDetails());

    }
}