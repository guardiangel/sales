package com.scotia.sales.service;

import com.scotia.sales.entity.OverflowList;
import com.scotia.sales.entity.OverflowListGoods;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Felix
 * 商品报溢Service
 */
public interface OverflowListService {
    /**
     * 根据id查询实体
     * @param id
     * @return
     */
    public OverflowList findById(Integer id);

    /**
     * 获取当天最大报溢单号
     * @return
     */
    public String getTodayMaxOverflowNumber();

    /**
     * 添加报溢单 以及所有报溢单商品 以及 修改商品的成本均价
     * @param overflowList 报溢单
     * @param overflowListGoodsList 报溢单商品
     */
    public void save(OverflowList overflowList, List<OverflowListGoods> overflowListGoodsList);

    /**
     * 根据条件查询报溢单信息
     * @param overflowList
     * @param direction
     * @param properties
     * @return
     */
    public List<OverflowList> list(OverflowList overflowList, Sort.Direction direction, String... properties);

    /**
     * 根据id删除报溢单信息 包括报溢单里的商品
     * @param id
     */
    public void delete(Integer id);
}
