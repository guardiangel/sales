package com.scotia.sales.repository;

import com.scotia.sales.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author
 *      Felix
 * 系统日志Repository接口
 */
public interface LogRepository extends JpaRepository<Log, Integer>, JpaSpecificationExecutor<Log> {

}
