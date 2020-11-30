package com.scotia.sales.repository;

import com.scotia.sales.entity.GoodsUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Felix
 */
public interface GoodsUnitRepository extends JpaRepository<GoodsUnit, Integer>,
        JpaSpecificationExecutor<GoodsUnit> {

}
