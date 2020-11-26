package com.scotia.sales.repository;

import com.scotia.sales.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author
 *      Felix
 *用户Repository接口,数据库操作层。
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * 根据用户名查找用户实体, nativeQuery表示使用原生sql
     * @param userName
     *      用户名
     * @return
     */
    @Query(value = "select * from t_user t where t.user_name=?1", nativeQuery = true)
    public User findByUserName(String userName);


}
