package com.scotia.sales.service.impl;

import com.scotia.sales.entity.Supplier;
import com.scotia.sales.repository.SupplierRepository;
import com.scotia.sales.service.SupplierService;
import com.scotia.sales.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author
 *      Felix
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> findByName(String name) {
        return supplierRepository.findByName(name);
    }

    @Override
    public Supplier findById(Integer id) {
        return supplierRepository.findById(id).orElse(new Supplier());
    }

    @Override
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Override
    public List<Supplier> list(Supplier supplier, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {

        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, direction, properties);

        Page<Supplier> pageSupplier = supplierRepository.findAll((Specification<Supplier>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (supplier != null) {
                if (StringUtil.isNotEmpty(supplier.getName())) {
                    predicate.getExpressions()
                            .add(criteriaBuilder.like(root.get("name"), "%" + supplier.getName() + "%"));
                }
            }

            return predicate;
        }, pageRequest);

        return pageSupplier.getContent();
    }

    @Override
    public Long getCount(Supplier supplier) {
        return supplierRepository.count(new Specification<Supplier>() {
            @Override
            public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (supplier != null) {
                    if (StringUtil.isNotEmpty(supplier.getName())) {
                        predicate.getExpressions()
                                .add(criteriaBuilder.like(root.get("name"), "%" + supplier.getName() + "%"));
                    }
                }
                return predicate;
            }
        });
    }

    @Override
    public void delete(Integer id) {
        supplierRepository.deleteById(id);
    }
}
