package com.scotia.sales.service;

import com.scotia.sales.entity.RoleMenu;

/**
 * @author Felix
 * 角色菜单service
 */
public interface RoleMenuService {
    /**
     * 根据角色id删除所有关联信息
     * @param roleId
     */
    public void deleteByRoleId(Integer roleId);

    /**
     * 保存
     * @param roleMenu
     */
    public void save(RoleMenu roleMenu);
}
