package com.scotia.sales.repository;

import com.scotia.sales.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *角色Repository接口
 */
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    /**
     *根据用户ID查询角色集合。
     * @param id
     * @return
     */
    @Query(value = "select r.* from t_user u, t_role r, t_user_role ur where ur.user_id=u.id and ur.role_id=r.id" +
            " and u.id=?1", nativeQuery = true)
    public List<Role> findByUserId(Integer id);


}
