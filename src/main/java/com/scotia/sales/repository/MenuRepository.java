package com.scotia.sales.repository;

import com.scotia.sales.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 权限Repository接口
 */
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    /**
     *根据ID获得菜单权限集合。
     * @param roleId
     * @return
     */
    @Query(value = "select  m.* from t_role r, t_menu m, t_role_menu rm where rm.role_id=r.id and rm.menu_id=m.id" +
            " and r.id=?1", nativeQuery = true)
    public List<Menu> findByRoleId(int roleId);

    /**
     * 根据父节点ID和角色ID，查询子节点
     *
     * @param parentId
     * @param roleId
     * @return
     */
    @Query(value = "select * from t_menu where p_id=?1 and id in (select menu_id from t_role_menu where role_id=?2)",
            nativeQuery = true)
    public List<Menu> findByParentIdAndRoleId(int parentId, int roleId);

    /**
     * 根据节点ID，查询所有子节点
     * @param parentId
     * @return
     */
    @Query(value = "select * From t_menu where p_id=?1", nativeQuery = true)
    public List<Menu> findByParentId(int parentId);
}
