package com.scotia.sales.repository;

import com.scotia.sales.entity.OverflowListGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Felix
 * 报溢Repository
 */
public interface OverflowListGoodsRepository extends JpaRepository<OverflowListGoods, Integer>,
        JpaSpecificationExecutor<OverflowListGoods> {

    /**
     * 查询报溢单对应的报溢单商品
     * @param overflowListid
     * @return
     */
    @Query(value = "select * from t_overflow_list_goods where overflow_list_id=?1",
            nativeQuery = true)
    List<OverflowListGoods> listByOverflowListId(Integer overflowListid);

    @Query(value = "delete from t_overflow_list_goods where overflow_list_id=?1",
            nativeQuery = true)
    @Modifying
    public void deleteByOverflowListId(Integer overflowListId);

}
