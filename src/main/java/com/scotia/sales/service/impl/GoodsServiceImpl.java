package com.scotia.sales.service.impl;

import com.scotia.sales.entity.Goods;
import com.scotia.sales.repository.GoodsRepository;
import com.scotia.sales.service.GoodsService;
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

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsRepository goodsRepository;

    @Override
    public Goods findById(Integer id) {
        return goodsRepository.findById(id).orElse(new Goods());
    }

    @Override
    public void save(Goods goods) {
        goodsRepository.save(goods);
    }

    @Override
    public List<Goods> list(Goods goods, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(direction, properties));

        Page<Goods> pageGoods = goodsRepository.findAll((Specification<Goods>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (goods != null) {
                if (StringUtil.isNotEmpty(goods.getName())) {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + goods.getName().trim() + "%"));
                }
                if (goods.getType() != null && goods.getType().getId() != null && goods.getType().getId() != 1) {
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("type").get("id"), goods.getType().getId()));
                }
                if (StringUtil.isNotEmpty(goods.getCodeOrName())) {
                    predicate.getExpressions()
                            .add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"), "%" + goods.getCodeOrName() + "%"),
                                    criteriaBuilder.like(root.get("name"), "%" + goods.getCodeOrName() + "%")));

                }
            }
            return predicate;
        }, pageRequest);

        return pageGoods.getContent();
    }

    @Override
    public List<Goods> listNoInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageable = PageRequest.of(page - 1, pageSize, Sort.by(direction, properties));
        Page<Goods> pageUser = goodsRepository.findAll((Specification<Goods>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (StringUtil.isNotEmpty(codeOrName)) {
                predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%" + codeOrName + "%"), cb.like(root.get("name"), "%" + codeOrName + "%")));
            }
            predicate.getExpressions().add(cb.equal(root.get("inventoryQuantity"), 0)); // 库存是0
            return predicate;
        }, pageable);
        return pageUser.getContent();
    }

    @Override
    public List<Goods> listHasInventoryQuantity(Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        PageRequest pageable = PageRequest.of(page - 1, pageSize, Sort.by(direction, properties));
        Page<Goods> pageUser = goodsRepository.findAll((Specification<Goods>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate.getExpressions().add(cb.greaterThan(root.get("inventoryQuantity"), 0)); // 库存不是0
            return predicate;
        }, pageable);
        return pageUser.getContent();
    }

    @Override
    public Long count(Goods goods) {
        return goodsRepository.count((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (goods != null) {
                if (StringUtil.isNotEmpty(goods.getName())) {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + goods.getName().trim() + "%"));
                }
                if (goods.getType() != null && goods.getType().getId() != null && goods.getType().getId() != 1) {
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("type").get("id"), goods.getType().getId()));
                }
                if (StringUtil.isNotEmpty(goods.getCodeOrName())) {
                    predicate.getExpressions()
                            .add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"), "%" + goods.getCodeOrName() + "%"),
                                    criteriaBuilder.like(root.get("name"), "%" + goods.getCodeOrName() + "%")));
                }
            }
            return predicate;
        });
    }

    @Override
    public Long getCountNoInventoryQuantityByCodeOrName(String codeOrName) {
        return goodsRepository.count((Specification<Goods>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (StringUtil.isNotEmpty(codeOrName)) {
                predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%" + codeOrName + "%"), cb.like(root.get("name"), "%" + codeOrName + "%")));
            }
            predicate.getExpressions().add(cb.equal(root.get("inventoryQuantity"), 0)); // 库存是0
            return predicate;
        });
    }

    @Override
    public Long getCountHasInventoryQuantity() {
        return goodsRepository.count(new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate.getExpressions().add(cb.greaterThan(root.get("inventoryQuantity"), 0)); // 库存不是0
                return predicate;
            }
        });
    }

    @Override
    public void delete(Integer id) {
        goodsRepository.deleteById(id);
    }

    @Override
    public String getMaxGoodsCode() {
        return goodsRepository.getMaxGoodsCode();
    }

    @Override
    public List<Goods> listAlarm() {
        return goodsRepository.listAlarm();
    }
}
