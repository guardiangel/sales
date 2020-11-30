package com.scotia.sales.repository;

import com.scotia.sales.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>,
        JpaSpecificationExecutor<UserRole> {


    /**
     * 删除用户ID关联的用户角色信息
     *
     * @param userId
     */
    @Query(value = "delete from t_user_role where user_id=?1", nativeQuery = true)
    @Modifying
    public void deleteByUserId(Integer userId);

    /**
     * 删除用户ID关联的用户角色信息
     *
     * @param roleId
     */
    @Query(value = "delete from t_user_role where role_id=?1", nativeQuery = true)
    @Modifying
    public void deleteByRoleId(Integer roleId);





}
