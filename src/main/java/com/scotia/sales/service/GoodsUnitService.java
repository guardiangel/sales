package com.scotia.sales.service;

import com.scotia.sales.entity.GoodsUnit;

import java.util.List;

/**
 * @author Felix
 * 商品单位Service接口
 */
public interface GoodsUnitService {
    /**
     * 根据id查询实体
     * @param id
     * @return
     */
    public GoodsUnit findById(Integer id);

    /**
     * 查询所有商品单位信息
     * @return
     */
    public List<GoodsUnit> listAll();

    /**
     * 修改或者修改商品单位信息
     * @param goodsUnit
     */
    public void save(GoodsUnit goodsUnit);

    /**
     * 根据id删除商品单位
     * @param id
     */
    public void delete(Integer id);
}
