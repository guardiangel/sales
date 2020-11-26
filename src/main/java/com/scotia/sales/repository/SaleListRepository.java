package com.scotia.sales.repository;

import com.scotia.sales.entity.SaleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Felix
 */
public interface SaleListRepository extends JpaRepository<SaleList, Integer>,
        JpaSpecificationExecutor<SaleList> {

    /**
     * 获得当天最大的销售单号
     *
     * @return
     */
    @Query(value = "SELECT MAX(sale_number) FROM t_sale_list WHERE TO_DAYS(sale_date) = TO_DAYS(NOW())", nativeQuery = true)
    public String getTodayMaxSaleNumber();

    /**
     * 按天统计某个日期范围内的信息
     * SELECT
     * SUM(t3.purchasing_price * t1.num) AS amountCost,
     * SUM(t1.price * t1.num) AS amountSale,
     * t2.sale_date AS saleDate
     * FROM
     * t_sale_list_goods t1
     * LEFT JOIN
     * t_sale_list t2 ON t1.sale_list_id = t2.id
     * LEFT JOIN
     * t_goods t3 ON t3.id = t1.goods_id
     * WHERE t2.sale_date
     * BETWEEN ?1 AND ?2 GROUP BY t2.sale_date
     *
     * @param begin
     * @param end
     * @return
     */
    @Query(value = "SELECT \n" +
            "    SUM(t3.purchasing_price * t1.num) AS amountCost,\n" +
            "    SUM(t1.price * t1.num) AS amountSale,\n" +
            "    t2.sale_date AS saleDate\n" +
            "FROM\n" +
            "    t_sale_list_goods t1\n" +
            "        LEFT JOIN\n" +
            "    t_sale_list t2 ON t1.sale_list_id = t2.id\n" +
            "        LEFT JOIN\n" +
            "    t_goods t3 ON t3.id = t1.goods_id\n" +
            "WHERE t2.sale_date\n" +
            " BETWEEN ?1 AND ?2 GROUP BY t2.sale_date ", nativeQuery = true)
    public List<Object> countSaleByDay(String begin, String end);

    /**
     * 按月统计某个范围内的信息
     * <p>
     * SELECT
     * SUM(t3.purchasing_price * t1.num) AS amountCost,
     * SUM(t1.price * t1.num) AS amountSale,
     * DATE_FORMAT(t2.sale_date, '%Y-%m') AS saleDate
     * FROM
     * t_sale_list_goods t1
     * LEFT JOIN
     * t_sale_list t2 ON t1.sale_list_id = t2.id
     * LEFT JOIN
     * t_goods t3 ON t3.id = t1.goods_id
     * WHERE DATE_FORMAT(t2.sale_date,'%Y-%m')
     * BETWEEN ?1 AND ?2
     * GROUP BY DATE_FORMAT(t2.sale_date,'%Y-%m')
     * </p>
     *
     * @param begin
     * @param end
     * @return
     */
    @Query(value = "SELECT \n" +
            "    SUM(t3.purchasing_price * t1.num) AS amountCost,\n" +
            "    SUM(t1.price * t1.num) AS amountSale,\n" +
            "    DATE_FORMAT(t2.sale_date, '%Y-%m') AS saleDate\n" +
            "FROM\n" +
            "    t_sale_list_goods t1\n" +
            "        LEFT JOIN\n" +
            "    t_sale_list t2 ON t1.sale_list_id = t2.id\n" +
            "        LEFT JOIN\n" +
            "    t_goods t3 ON t3.id = t1.goods_id\n" +
            "WHERE DATE_FORMAT(t2.sale_date,'%Y-%m') \n" +
            "BETWEEN ?1 AND ?2 \n" +
            "GROUP BY DATE_FORMAT(t2.sale_date,'%Y-%m')", nativeQuery = true)
    public List<Object> countSaleByMonth(String begin, String end);

}
