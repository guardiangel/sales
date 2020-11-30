package com.scotia.sales.service;

import com.scotia.sales.entity.UserRole;

/**
 * @author Felix
 * 用户角色接口
 */
public interface UserRoleService {
    /**
     * 添加或者修改用户角色关联
     * @param userRole
     */
    public void save(UserRole userRole);

    /**
     * 删除用户角色关联实体
     * @param userRole
     */
    public void delete(UserRole userRole);



    /**
     * 根据ID查询用户角色关联实体
     * @param id
     * @return
     */
    public UserRole findById(Integer id);

    /**
     * 根据用户id删除所有关联信息
     * @param userId
     */
    public void deleteByUserId(Integer userId);

    /**
     * 根据角色id删除所有关联信息
     * @param roleId
     */
    public void deleteByRoleId(Integer roleId);
}
