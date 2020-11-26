package com.scotia.sales.service;

import com.scotia.sales.entity.Customer;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Felix
 */
public interface CustomerService {
    /**
     * 根据名称模糊查询供应商
     *
     * @param name
     * @return
     */
    public List<Customer> findByName(String name);

    /**
     * 根据id精确查询
     *
     * @param id
     * @return
     */
    public Customer findById(Integer id);

    /**
     * 保存或修改供应商
     * @param customer
     */
    public void save(Customer customer);

    /**
     * 分页查询
     * @param customer
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Customer> list(Customer customer, Integer page, Integer pageSize,
                               Sort.Direction direction, String... properties);

    /**
     * 获得记录总数
     * @param customer
     * @return
     */
    public Long getCount(Customer customer);

    /**
     * 根据id删除供应商
     * @param id
     */
    public void delete(Integer id);
}
