package com.scotia.sales.service.impl;

import com.scotia.sales.entity.RoleMenu;
import com.scotia.sales.repository.RoleMenuRepository;
import com.scotia.sales.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Felix
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService {

    @Resource
    private RoleMenuRepository roleMenuRepository;

    @Override
    public void deleteByRoleId(Integer roleId) {
        roleMenuRepository.deleteByRoleId(roleId);
    }

    @Override
    public void save(RoleMenu roleMenu) {
        roleMenuRepository.save(roleMenu);
    }
}
