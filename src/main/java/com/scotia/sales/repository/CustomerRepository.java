package com.scotia.sales.repository;

import com.scotia.sales.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Felix
 * 客户操作Repository接口
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer>,
        JpaSpecificationExecutor<Customer> {
    /**
     * 根据姓名查询供应商
     * @param name
     * @return
     */
    @Query(value = "select * from t_customer t  where t.name like ?1", nativeQuery = true)
    List<Customer> findByName(String name);
}
