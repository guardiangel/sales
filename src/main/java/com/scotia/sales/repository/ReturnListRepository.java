package com.scotia.sales.repository;

import com.scotia.sales.entity.ReturnList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author
 *      Felix
 * 退货单Repository
 */
public interface ReturnListRepository extends
        JpaRepository<ReturnList, Integer>, JpaSpecificationExecutor<ReturnList> {

    /**
     * 获得当天最大的退货单号
     * @return
     */
    @Query(value = "SELECT MAX(return_number) FROM t_return_list WHERE TO_DAYS(return_date) = TO_DAYS(NOW())", nativeQuery = true)
    String getTodayMaxReturnNumber();
}
