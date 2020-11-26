package com.scotia.sales.service.impl;

import com.scotia.sales.entity.Customer;
import com.scotia.sales.repository.CustomerRepository;
import com.scotia.sales.service.CustomerService;
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
 * @author Felix
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Customer findById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> list(Customer customer, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Customer> pageUser = customerRepository.findAll((Specification<Customer>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (customer != null) {
                if (StringUtil.isNotEmpty(customer.getName())) {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + customer.getName().trim() + "%"));
                }
            }
            return predicate;
        }, pageRequest);

        return pageUser.getContent();
    }

    @Override
    public Long getCount(Customer customer) {
        return customerRepository.count((Specification<Customer>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (customer != null) {
                if (StringUtil.isNotEmpty(customer.getName())) {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + customer.getName().trim() + "%"));
                }
            }
            return predicate;
        });
    }

    @Override
    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }
}
