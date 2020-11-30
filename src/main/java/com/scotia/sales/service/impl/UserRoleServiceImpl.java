package com.scotia.sales.service.impl;

import com.scotia.sales.entity.UserRole;
import com.scotia.sales.repository.UserRoleRepository;
import com.scotia.sales.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户角色操作接口
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleRepository userRoleRepository;

    @Override
    public void save(UserRole userRole) {

    }

    @Override
    public void delete(UserRole userRole) {

    }

    @Override
    public UserRole findById(Integer id) {
        return null;
    }

    @Override
    public void deleteByUserId(Integer userId) {

    }

    @Override
    public void deleteByRoleId(Integer userId) {

    }
}
