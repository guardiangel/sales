package com.scotia.sales.repository;

import com.scotia.sales.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 供应商Repository接口
 */
public interface SupplierRepository extends JpaRepository<Supplier, Integer>,
        JpaSpecificationExecutor<Supplier> {

    /**
     * 根据姓名查询供应商
     * @param name
     * @return
     */
    @Query(value = "select * from t_supplier t  where t.name like ?1", nativeQuery = true)
    List<Supplier> findByName(String name);


}
