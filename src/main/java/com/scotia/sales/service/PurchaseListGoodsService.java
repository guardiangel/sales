package com.scotia.sales.service;

import com.scotia.sales.entity.PurchaseListGoods;

import java.util.List;

/**
 * @author
 *      Felix
 * 进货单商品Service接口
 */
public interface PurchaseListGoodsService {

    /**
     * 根据进货单ID，查询所有的进货单商品
     *
     * @param purchaseListId
     * @return
     */
    public List<PurchaseListGoods> listByPurchaseListId(Integer purchaseListId);

    /**
     * 根据条件查询进货单商品
     * @param purchaseListGoods
     * @return
     */
    public List<PurchaseListGoods> list(PurchaseListGoods purchaseListGoods);




}
