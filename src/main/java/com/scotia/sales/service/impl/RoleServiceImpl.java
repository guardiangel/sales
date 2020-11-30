package com.scotia.sales.service.impl;

import com.scotia.sales.entity.Role;
import com.scotia.sales.repository.RoleRepository;
import com.scotia.sales.service.RoleService;
import com.scotia.sales.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author
 *      Felix
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Override
    public List<Role> findByUserId(Integer userId) {
        return roleRepository.findByUserId(userId);
    }

    @Override
    public Role findById(Integer roleId) {
        return roleRepository.findById(roleId).orElse(new Role());
    }

    @Override
    public List<Role> listAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> list(Role role, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(direction, properties));
        Page<Role> pageRole =roleRepository.findAll((Specification<Role>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (role != null) {
                if (StringUtil.isNotEmpty(role.getName())) {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + role.getName().trim() + "%"));
                }
                predicate.getExpressions().add(criteriaBuilder.notEqual(root.get("id"), 1));//排除管理员
            }
            return predicate;
        }, pageRequest);
        return pageRole.getContent();
    }

    @Override
    public Long getCount(Role role) {
        return roleRepository.count((Specification<Role>) (root, query, cb) -> {
            Predicate predicate=cb.conjunction();
            if(role!=null){
                if(StringUtil.isNotEmpty(role.getName())){
                    predicate.getExpressions().add(cb.like(root.get("name"), "%"+role.getName().trim()+"%"));
                }
                predicate.getExpressions().add(cb.notEqual(root.get("id"), 1)); // 管理员角色除外
            }
            return predicate;
        });
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }
}
