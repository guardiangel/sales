package com.scotia.sales.service;

import com.scotia.sales.entity.Menu;

import java.util.List;

/**
 * @author
 *      Felix
 * 菜单操作接口
 */
public interface MenuService {

    /**
     * 根据ID查询菜单实体
     * @param id
     * @return
     */
    public Menu findById(Integer id);

    /**
     * 根据角色ID，查询权限菜单集合
     *
     * @param roleId
     * @return
     */
    public List<Menu> findByRoleId(Integer roleId);

    /**
     * 根据父接口和角色ID查询子节点
     *
     * @param parentId
     * @param roleId
     * @return
     */
    public List<Menu> findByParentIdAndRoleId(int parentId, int roleId);

    /**
     * 根据父节点，查询所有子节点
     * @param parentId
     * @return
     */
    public List<Menu> findByParentId(int parentId);

}
