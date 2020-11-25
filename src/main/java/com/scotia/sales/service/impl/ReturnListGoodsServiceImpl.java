package com.scotia.sales.service.impl;

import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.ReturnListGoods;
import com.scotia.sales.repository.ReturnListGoodsRepository;
import com.scotia.sales.service.ReturnListGoodsService;
import com.scotia.sales.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service("returnListGoodsService")
public class ReturnListGoodsServiceImpl implements ReturnListGoodsService {

    @Resource
    private ReturnListGoodsRepository returnListGoodsRepository;

    @Override
    public List<ReturnListGoods> listByReturnListId(Integer returnListId) {
        return returnListGoodsRepository.listByReturnListId(returnListId);
    }

    @Override
    public List<ReturnListGoods> list(ReturnListGoods returnListGoods) {
        return returnListGoodsRepository.findAll(new Specification<ReturnListGoods>() {
            @Override
            public Predicate toPredicate(Root<ReturnListGoods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (returnListGoods != null) {
                    //根据商品类型
                    if (returnListGoods.getType() != null
                            && returnListGoods.getType().getId() != null
                            && returnListGoods.getType().getId() != ConstantParam.GOODSTYPE_ROOT_ID) {
                        predicate.getExpressions()
                                .add(criteriaBuilder.equal(root.get("type").get("id"),
                                        returnListGoods.getType().getId()));
                    }
                    //根据商品代码或名称
                    if (StringUtil.isNotEmpty(returnListGoods.getCodeOrName())) {
                        predicate.getExpressions().add(criteriaBuilder.or(
                                criteriaBuilder.like(root.get("code"), "%" + returnListGoods.getCodeOrName() + "%"),
                                criteriaBuilder.like(root.get("name"), "%" + returnListGoods.getCodeOrName() + "%")
                        ));
                    }

                    //根据退货单号
                    if (returnListGoods.getReturnList() != null
                            && StringUtil.isNotEmpty(returnListGoods.getReturnList().getReturnNumber())) {
                        predicate.getExpressions().add(
                                criteriaBuilder.like(root.get("returnList").get("returnNumber"),
                                        "%" + returnListGoods.getReturnList().getReturnNumber() + "%")
                        );
                    }
                }
                return predicate;
            }
        });
    }
}
