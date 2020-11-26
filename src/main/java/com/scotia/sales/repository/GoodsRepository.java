package com.scotia.sales.repository;

import com.scotia.sales.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author
 *      Felix
 * 商品Repository接口
 */
public interface GoodsRepository extends JpaRepository<Goods, Integer>, JpaSpecificationExecutor<Goods> {
    /**
     * 获取最大的商品编号
     * @return
     */
    @Query(value = "select max(code) from t_goods", nativeQuery = true)
    public String getMaxGoodsCode();

    /**
     *查询库存报警商品 库存小于库存下限的商品
     * @return
     */
    @Query(value = "select t.* From t_goods t where t.inventory_quantity < t.min_num", nativeQuery = true)
    public List<Goods> listAlarm();

}
