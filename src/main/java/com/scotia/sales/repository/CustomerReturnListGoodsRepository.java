package com.scotia.sales.repository;

import com.scotia.sales.entity.CustomerReturnListGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author
 *      Felix
 */
public interface CustomerReturnListGoodsRepository
        extends JpaRepository<CustomerReturnListGoods, Integer>, JpaSpecificationExecutor<CustomerReturnListGoods> {

    /**
     * 根据客户退货单id查询所有客户退货单商品
     *
     * @param customerReturnListId
     * @return
     */
    @Query(value = "select * from t_customer_return_list_goods where customer_return_list_id=?1", nativeQuery = true)
    public List<CustomerReturnListGoods> listByCustomerReturnListId(Integer customerReturnListId);

    /**
     * <p>删除指定客户退货单的所有商品</p>
     * 1.在@Query注解中编写JPQL实现DELETE和UPDATE操作的时候必须加上@modifying注解，以通知Spring Data 这是一个DELETE或UPDATE操作
     * 2.UPDATE或者DELETE操作需要使用事务，此时需要 定义Service层，在Service层的方法上添加事务操作
     * 3.注意JPQL不支持INSERT操作。
     * 4.默认情况下， SpringData 的每个方法上有事务， 但都是一个只读事务。 他们不能完成修改操作。
     *
     * @param customerReturnListId
     */
    @Query(value = "delete from t_customer_return_list_goods where customer_return_list_id=?1", nativeQuery = true)
    @Modifying
    public void deleteByCustomerReturnListId(Integer customerReturnListId);

    /**
     * 统计某个商品的客户退货总量
     * @param goodsId
     * @return
     */
    @Query(value = "SELECT SUM(num) AS total FROM t_customer_return_list_goods WHERE goods_id=?1", nativeQuery = true)
    public Integer getTotalByGoodsId(Integer goodsId);



}
