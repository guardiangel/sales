package com.scotia.sales.repository;

import com.scotia.sales.entity.OverflowList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Felix
 */
public interface OverflowListRepository extends JpaRepository<OverflowList, Integer>,
        JpaSpecificationExecutor<OverflowList> {
    /**
     * 获取当天最大报溢单号
     *
     * @return
     */
    @Query(value = "SELECT MAX(overflow_number) FROM t_overflow_list WHERE TO_DAYS(overflow_date) = TO_DAYS(NOW())", nativeQuery = true)
    String getTodayMaxOverflowNumber();
}
