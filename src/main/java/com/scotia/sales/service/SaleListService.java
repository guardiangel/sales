package com.scotia.sales.service;

import com.scotia.sales.entity.SaleList;
import com.scotia.sales.entity.SaleListGoods;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Felix
 */
public interface SaleListService {

    /**
     * 根据ID，查询销售的商品实体
     *
     * @param id
     * @return
     */
    SaleList findById(Integer id);

    /**
     * 获得当天最大的销售单号
     *
     * @return
     */
    String getTodayMaxSaleNumber();

    /**
     * 添加销售单 以及所有销售单商品 以及 修改商品的成本均价
     *
     * @param saleList
     * @param saleListGoodsList
     */
    void save(SaleList saleList, List<SaleListGoods> saleListGoodsList);

    /**
     * 根据条件，查询销售单信息
     *
     * @param saleList
     * @param direction
     * @param properties
     * @return
     */
    List<SaleList> list(SaleList saleList, Sort.Direction direction, String... properties);

    /**
     * 根据id删除销售单
     *
     * @param saleListId
     */
    void delete(Integer saleListId);

    /**
     * 更新销售单
     *
     * @param saleList
     */
    void update(SaleList saleList);

    /**
     * 按天统计某个日期内的销售信息
     *
     * @param begin
     * @param end
     * @return
     */
    List<Object> countSaleByDay(String begin, String end);

    /**
     * 按月统计某个日期内的销售信息
     *
     * @param begin
     * @param end
     * @return
     */
    List<Object> countSaleByMonth(String begin, String end);

}
