package com.scotia.sales.service;

import com.scotia.sales.entity.PurchaseList;
import com.scotia.sales.entity.PurchaseListGoods;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author
 *      Felix
 * 进货单service接口
 */
public interface PurchaseListService {

    /**
     * 根据id查询进货单实体
     *
     * @param id
     * @return
     */
    PurchaseList findById(Integer id);

    /**
     * 获得当天最大的进货单号
     * @return
     */
    String getTodayMaxPurchaseNumber();

    /**
     * 保存进货单以及对应的进货单商品
     *
     * @param purchaseList
     * @param purchaseListGoodsList
     */
    void save(PurchaseList purchaseList, List<PurchaseListGoods> purchaseListGoodsList);

    /**
     * 根据条件查询进货单信息
     * @param purchaseList
     * @param direction
     * @param properties
     * @return
     */
    public List<PurchaseList> list(PurchaseList purchaseList, Sort.Direction direction, String... properties);

    /**
     * 根据id删除进货单信息 包括进货单里的商品
     * @param id
     */
    public void delete(Integer id);

    /**
     * 更新进货单
     * @param purchaseList
     */
    public void update(PurchaseList purchaseList);


}
