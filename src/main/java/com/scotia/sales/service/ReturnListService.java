package com.scotia.sales.service;

import com.scotia.sales.entity.ReturnList;
import com.scotia.sales.entity.ReturnListGoods;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author
 *      Felix
 * 退货单service接口
 */
public interface ReturnListService {
    /**
     * 根据退货单id，查询退货单
     * @param id
     * @return
     */
    public ReturnList findById(Integer id);

    /**
     * 获得当天最大的退货单号
     * @return
     */
    public String getTodayMaxReturnNumber();

    /**
     * 保存退货单号及退货单商品
     *
     * @param returnList
     * @param returnListGoodsList
     */
    public void save(ReturnList returnList, List<ReturnListGoods> returnListGoodsList);

    /**
     * 根据条件查询退货单信息
     *
     * @param returnList
     * @param direction
     * @param properties
     * @return
     */
    public List<ReturnList> list(ReturnList returnList, Sort.Direction direction, String... properties);

    /**
     * 根据退货单ID，删除退货单及退货单商品
     * @param id
     */
    public void delete(Integer id);

    /**
     * 更新退货单
     * @param returnList
     */
    public void update(ReturnList returnList);

}
