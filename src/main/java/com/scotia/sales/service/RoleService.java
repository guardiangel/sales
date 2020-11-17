package com.scotia.sales.service;

import com.scotia.sales.entity.Role;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 角色实现接口
 */
public interface RoleService {

    /**
     * 根据用户ID，查询用户角色
     *
     * @param userId
     * @return
     */
    public List<Role> findByUserId(Integer userId);

    /**
     * 根据ID，查询角色实体
     * @param roleId
     * @return
     */
    public Role findById(Integer roleId);

    /**
     * 查询所有角色
     *
     * @return
     */
    public List<Role> listAll();

    /**
     * 根据分页条件查询角色信息
     *
     * @param role
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<Role> list(Role role, Integer page, Integer pageSize, Sort.Direction direction, String... properties);


    /**
     * 获取记录总数
     * @param role
     * @return
     */
    public Long getCount(Role role);

    /**
     * 添加或修改角色信息
     * @param role
     */
    public void save(Role role);

    /**
     *根据ID删除角色信息
     * @param id
     */
    public void delete(Integer id);

}
