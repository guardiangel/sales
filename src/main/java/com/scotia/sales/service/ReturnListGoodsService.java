package com.scotia.sales.service;


import com.scotia.sales.entity.ReturnListGoods;

import java.util.List;

/**
 * 退货单商品Service接口
 */
public interface ReturnListGoodsService {

    /**
     *根据退货单ID，查询所有的退货单商品
     * @param returnListId
     * @return
     */
    public List<ReturnListGoods> listByReturnListId(Integer returnListId);

    /**
     * 根据某个退货单商品，查询对应退货单下所有的退货单商品
     * @param returnListGoods
     * @return
     */
    public List<ReturnListGoods> list(ReturnListGoods returnListGoods);


}
