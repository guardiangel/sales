package com.scotia.sales.repository;

import com.scotia.sales.entity.PurchaseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author
 *      Felix
 * 进货单Repository
 */
public interface PurchaseListRepository
        extends JpaRepository<PurchaseList, Integer>, JpaSpecificationExecutor<PurchaseList> {

    /**
     * 获得当天最大的进货单号
     * @return
     */
    @Query(value = "SELECT MAX(purchase_number) FROM t_purchase_list WHERE TO_DAYS(purchase_date) = TO_DAYS(NOW())",nativeQuery = true)
    public String getTodayMaxPurchaseNumber();
}
