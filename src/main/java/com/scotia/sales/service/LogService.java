package com.scotia.sales.service;

import com.scotia.sales.entity.Log;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 日志操作接口
 */
public interface LogService {

    /**
     * 保存或修改日志实体
     * @param log
     */
    public void save(Log log);

    /**
     *根据分页查询日志信息
     * @param log
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Log> list(Log log, Integer page, Integer pageSize, Sort.Direction direction, String... properties);

    /**
     * 查询日志总数
     * @param log
     * @return
     */
    public Long getCount(Log log);

}
