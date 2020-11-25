package com.scotia.sales.service;

import com.scotia.sales.entity.GoodsType;

import java.util.List;

/**
 * 商品类别的service接口
 */
public interface GoodsTypeService {

    /**
     * 根据ID查询商品类别
     * @param id
     * @return
     */
    public GoodsType findById(Integer id);

    /*
    根据父ID，查询所有商品类别
     */
    public List<GoodsType> findByParentId(int parentId);

    /**
     * 保存或修改商品类别
     * @param goodsType
     */
    public void save(GoodsType goodsType);

    /**
     * 根据ID，删除商品类别
     * @param id
     */
    public void delete(Integer id);

    /**
     * 根据商品类型名称和父ID，保存当前商品类型，同时修改父商品类型的state
     * 定义此方法，是因为其中有两步保存动作，需要放在同一个事务里面。
     * @param name
     * @param parentId
     */
    public void saveCurrentGoodsTypeAndParentGoodsType(String name, Integer parentId);
}
