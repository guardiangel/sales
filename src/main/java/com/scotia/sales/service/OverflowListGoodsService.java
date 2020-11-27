package com.scotia.sales.service;

import com.scotia.sales.entity.OverflowListGoods;

import java.util.List;

/**
 * @author Felix
 * 报溢商品操作Service
 */
public interface OverflowListGoodsService {
    /**
     * 根据报溢单id查询所有报溢单商品
     *
     * @param overflowListId
     * @return
     */
    public List<OverflowListGoods> listByOverflowListId(Integer overflowListId);
}
