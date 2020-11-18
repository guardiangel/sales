package com.scotia.sales.service.impl;

import com.scotia.sales.entity.SaleListGoods;
import com.scotia.sales.repository.SaleListGoodsRepository;
import com.scotia.sales.service.SaleListGoodsService;
import com.scotia.sales.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Service("saleListGoodsService")
public class SaleListGoodsServiceImpl implements SaleListGoodsService {

    @Resource
    private SaleListGoodsRepository saleListGoodsRepository;

    @Override
    public List<SaleListGoods> listBySaleListId(Integer saleListId) {
        return saleListGoodsRepository.listBySaleListId(saleListId);
    }

    @Override
    public Integer getTotalByGoodsId(Integer goodsId) {
        Integer count = saleListGoodsRepository.getTotalByGoodsId(goodsId);
        return count == null ? 0 : count;
    }

    @Override
    public List<SaleListGoods> list(SaleListGoods saleListGoods) {
        return saleListGoodsRepository.findAll((Specification<SaleListGoods>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (saleListGoods != null) {
                if (saleListGoods.getType() != null
                        && saleListGoods.getType().getId() != null
                        && saleListGoods.getType().getId() != 1) {
                    predicate.getExpressions()
                            .add(criteriaBuilder.equal(root.get("type").get("id"),
                                    saleListGoods.getType().getId()));
                }

                if (StringUtil.isNotEmpty(saleListGoods.getCodeOrName())) {
                    predicate.getExpressions()
                            .add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"),
                                    "%" + saleListGoods.getCodeOrName() + "%"),
                                    criteriaBuilder.like(root.get("name"),
                                            "%" + saleListGoods.getCodeOrName() + "%")));
                }
                if (saleListGoods.getSaleList() != null
                        && StringUtil.isNotEmpty(saleListGoods.getSaleList().getSaleNumber())) {
                    predicate.getExpressions()
                            .add(criteriaBuilder.like(root.get("saleList").get("saleNumber"),
                                    "%" + saleListGoods.getSaleList().getSaleNumber() + "%"));
                }

            }
            return predicate;
        });
    }
}
