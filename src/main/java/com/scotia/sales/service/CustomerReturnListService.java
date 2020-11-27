package com.scotia.sales.service;

import com.scotia.sales.entity.CustomerReturnList;
import com.scotia.sales.entity.CustomerReturnListGoods;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Felix
 * 客户退货Service
 */
public interface CustomerReturnListService {
    /**
     * 根据id查询实体
     * @param id
     * @return
     */
    public CustomerReturnList findById(Integer id);

    /**
     * 获取当天最大客户退货单号
     * @return
     */
    public String getTodayMaxCustomerReturnNumber();

    /**
     * 添加客户退货单 以及所有客户退货单商品
     * @param customerReturnList 客户退货单
     * @param customerReturnListGoodsList 客户退货单商品
     */
    public void save(CustomerReturnList customerReturnList, List<CustomerReturnListGoods> customerReturnListGoodsList);

    /**
     * 根据条件查询客户退货单信息
     * @param customerReturnList
     * @param direction
     * @param properties
     * @return
     */
    public List<CustomerReturnList> list(CustomerReturnList customerReturnList, Sort.Direction direction, String... properties);

    /**
     * 根据id删除客户退货单信息 包括客户退货单里的商品
     * @param id
     */
    public void delete(Integer id);

    /**
     * 更新退货单
     * @param customerReturnList
     */
    public void update(CustomerReturnList customerReturnList);
}
