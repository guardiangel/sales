package com.scotia.sales.service.impl;

import com.scotia.sales.entity.Menu;
import com.scotia.sales.repository.MenuRepository;
import com.scotia.sales.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;

    @Override
    public Menu findById(Integer id) {
        return menuRepository.findById(id).orElse(new Menu());
    }

    @Override
    public List<Menu> findByRoleId(Integer roleId) {
        return menuRepository.findByRoleId(roleId);
    }

    @Override
    public List<Menu> findByParentIdAndRoleId(int parentId, int roleId) {
        return menuRepository.findByParentIdAndRoleId(parentId, roleId);
    }

    @Override
    public List<Menu> findByParentId(int parentId) {
        return menuRepository.findByParentId(parentId);
    }
}
