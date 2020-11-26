package com.scotia.sales.service.impl;

import com.scotia.sales.entity.PurchaseListGoods;
import com.scotia.sales.repository.PurchaseListGoodsRepository;
import com.scotia.sales.service.PurchaseListGoodsService;
import com.scotia.sales.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

/***
 * @author
 *      Felix
 */
@Service("purchaseListGoodsService")
public class PurchaseListGoodsServiceImpl implements PurchaseListGoodsService {

    @Resource
    private PurchaseListGoodsRepository purchaseListGoodsRepository;


    @Override
    public List<PurchaseListGoods> listByPurchaseListId(Integer purchaseListId) {
        return purchaseListGoodsRepository.listByPurchaseListId(purchaseListId);
    }

    @Override
    public List<PurchaseListGoods> list(PurchaseListGoods purchaseListGoods) {

        return purchaseListGoodsRepository.findAll((Specification<PurchaseListGoods>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (purchaseListGoods != null) {
                if (purchaseListGoods.getType() != null && purchaseListGoods.getType().getId() != null && purchaseListGoods.getType().getId() != 1) {
                    predicate.getExpressions().add(cb.equal(root.get("type").get("id"), purchaseListGoods.getType().getId()));
                }
                if (StringUtil.isNotEmpty(purchaseListGoods.getCodeOrName())) {
                    predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%" + purchaseListGoods.getCodeOrName() + "%"), cb.like(root.get("name"), "%" + purchaseListGoods.getCodeOrName() + "%")));
                }
                if (purchaseListGoods.getPurchaseList() != null && StringUtil.isNotEmpty(purchaseListGoods.getPurchaseList().getPurchaseNumber())) {
                    predicate.getExpressions().add(cb.like(root.get("purchaseList").get("purchaseNumber"), "%" + purchaseListGoods.getPurchaseList().getPurchaseNumber() + "%"));
                }
            }
            return predicate;
        });
    }
}
