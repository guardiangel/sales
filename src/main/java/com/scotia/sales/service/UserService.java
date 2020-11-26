package com.scotia.sales.service;

import com.scotia.sales.entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author
 *      Felix
 * 用户操作实现接口
 */
public interface UserService {


    /**
     * 根据用户名查询实体
     * @param userName
     * @return
     *      用户对象
     */
    public User findByUserName(String userName);

    /**
     * 根据用户ID查询实体
     * @param id
     * @return
     */
    public User findByUserId(Integer id);

    /**
     * 添加或修改用户信息
     *
     * @param user
     */
    public void save(User user);

    /**
     * 根据分页条件查询用户信息
     *
     * @param user
     * @param page
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    public List<User> list(User user, Integer page, Integer pageSize, Sort.Direction direction, String... properties);

    /**
     * 获得记录总数
     * @param user
     * @return
     */
    public Long getCount(User user);

    /**
     * 根据ID删除用户
     * @param userId
     */
    public void delete(Integer userId);

}
