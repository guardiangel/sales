package com.scotia.sales.repository;

import com.scotia.sales.entity.ReturnListGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 退货单商品Repository接口
 */
public interface ReturnListGoodsRepository extends
        JpaRepository<ReturnListGoods, Integer>, JpaSpecificationExecutor<ReturnListGoods> {

    /**
     * 根据退货单id，查询所有的退货单商品
     *
     * @param returnListId
     * @return
     */
    @Query(value = "select * from t_return_list_goods where return_list_id=?1", nativeQuery = true)
    public List<ReturnListGoods> listByReturnListId(Integer returnListId);

    /**
     * 删除指定退货单下所有的退货单商品
     *
     * @param returnListId
     */
    @Query(value = "delete from t_return_list_goods where return_list_id=?1", nativeQuery = true)
    @Modifying
    public void deleteByReturnListId(Integer returnListId);

}
