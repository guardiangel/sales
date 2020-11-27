package com.scotia.sales.service;

import com.scotia.sales.entity.DamageListGoods;

import java.util.List;

/**
 * @author Felix
 */
public interface DamageListGoodsService {
    /**
     * 根据报损单id查询所有报损单商品
     * @param damageListId
     * @return
     */
    public List<DamageListGoods> listByDamageListId(Integer damageListId);
}
