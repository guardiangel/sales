package com.scotia.sales.service.impl;

import com.scotia.sales.entity.UserRole;
import com.scotia.sales.repository.RoleMenuRepository;
import com.scotia.sales.repository.RoleRepository;
import com.scotia.sales.repository.UserRoleRepository;
import com.scotia.sales.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * 用户角色操作接口
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleRepository userRoleRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private RoleMenuRepository roleMenuRepository;

    @Override
    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    @Override
    public void delete(UserRole userRole) {
        userRoleRepository.delete(userRole);
    }

    @Override
    public UserRole findById(Integer id) {
        return userRoleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteByUserId(Integer userId) {
        userRoleRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteByRoleId(Integer roleId) {
        userRoleRepository.deleteByRoleId(roleId);
        roleMenuRepository.deleteByRoleId(roleId);
        roleRepository.deleteById(roleId);

    }
}
