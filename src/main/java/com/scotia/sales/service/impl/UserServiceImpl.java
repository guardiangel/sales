package com.scotia.sales.service.impl;

import com.scotia.sales.entity.User;
import com.scotia.sales.repository.UserRepository;
import com.scotia.sales.service.UserService;
import com.scotia.sales.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);

    }

    @Override
    public User findByUserId(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> list(User user, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<User> pageUser = userRepository.findAll((Specification<User>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (user != null) {
                if (StringUtil.isNotEmpty(user.getUserName())) {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("userName"), "%" + user.getUserName().trim() + "%"));
                }
                predicate.getExpressions().add(criteriaBuilder.notEqual(root.get("id"), 1));
            }
            return predicate;
        }, pageRequest);

        return pageUser.getContent();
    }

    @Override
    public Long getCount(User user) {
        return userRepository.count((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (user != null) {
                if (StringUtil.isNotEmpty(user.getUserName())) {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("userName"), "%" + user.getUserName().trim() + "%"));
                }
                predicate.getExpressions().add(criteriaBuilder.notEqual(root.get("id"), 1));
            }
            return predicate;
        });
    }

    @Override
    public void delete(Integer userId) {
        userRepository.deleteById(userId);
    }
}
