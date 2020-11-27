package com.scotia.sales.service;

import com.scotia.sales.entity.DamageList;
import com.scotia.sales.entity.DamageListGoods;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Felix
 * 商品报损接口
 */
public interface DamageListService {
    /**
     * 根据id查询实体
     * @param id
     * @return
     */
    public DamageList findById(Integer id);

    /**
     * 获取当天最大报损单号
     * @return
     */
    public String getTodayMaxDamageNumber();

    /**
     * 添加报损单 以及所有报损单商品 以及 修改商品的成本均价
     * @param damageList 报损单
     * @param damageListGoodsList 报损单商品
     */
    public void save(DamageList damageList, List<DamageListGoods> damageListGoodsList);

    /**
     * 根据条件查询报损单信息
     * @param damageList
     * @param direction
     * @param properties
     * @return
     */
    public List<DamageList> list(DamageList damageList, Sort.Direction direction, String... properties);

    /**
     * 根据id删除报损单信息 包括报损单里的商品
     * @param id
     */
    public void delete(Integer id);
}
