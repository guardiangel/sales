package com.scotia.sales.service;


import com.scotia.sales.entity.Supplier;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author
 *      Felix
 * 供应商service接口
 */
public interface SupplierService {
    /**
     * 根据名称模糊查询供应商
     *
     * @param name
     * @return
     */
    public List<Supplier> findByName(String name);

    /**
     * 根据id精确查询
     *
     * @param id
     * @return
     */
    public Supplier findById(Integer id);

    /**
     * 保存或修改供应商
     * @param supplier
     */
    public void save(Supplier supplier);

    /**
     * 分页查询
     * @param supplier
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Supplier> list(Supplier supplier, Integer page, Integer pageSize,
                               Sort.Direction direction, String... properties);

    /**
     * 获得记录总数
     * @param supplier
     * @return
     */
    public Long getCount(Supplier supplier);

    /**
     * 根据id删除供应商
     * @param id
     */
    public void delete(Integer id);

}
