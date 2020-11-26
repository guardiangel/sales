package com.scotia.sales.service;


import com.scotia.sales.entity.SaleListGoods;

import java.util.List;

/**
 * @author
 *      Felix
 * 销售商品Service接口
 */
public interface SaleListGoodsService {

    /**
     * 根据销售单ID，查询所有销售单产品
     * @param saleListId
     * @return
     */
    public List<SaleListGoods> listBySaleListId(Integer saleListId);

    /**
     * 统计某个商品的销售总量
     * @param goodsId
     * @return
     */
    public Integer getTotalByGoodsId(Integer goodsId);

    /**
     * 根据条件查询销售单所有产品
     * @param saleListGoods
     * @return
     */
    public List<SaleListGoods> list(SaleListGoods saleListGoods);
}