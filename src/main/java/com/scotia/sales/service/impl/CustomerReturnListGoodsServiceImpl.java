package com.scotia.sales.service.impl;

import com.scotia.sales.entity.CustomerReturnListGoods;
import com.scotia.sales.repository.CustomerReturnListGoodsRepository;
import com.scotia.sales.service.CustomerReturnListGoodsService;
import com.scotia.sales.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service("customerReturnListGoodsService")
public class CustomerReturnListGoodsServiceImpl implements CustomerReturnListGoodsService {

    @Resource
    private CustomerReturnListGoodsRepository customerReturnListGoodsRepository;

    /**
     * 根据客户退货单id查询所有客户退货单商品
     * @param customerReturnListId
     * @return
     */
    @Override
    public List<CustomerReturnListGoods> listByCustomerReturnListId(Integer customerReturnListId) {
        return customerReturnListGoodsRepository.listByCustomerReturnListId(customerReturnListId);
    }

    /**
     * 统计某个商品的客户退货总量
     * @param goodsId
     * @return
     */
    @Override
    public Integer getTotalByGoodsId(Integer goodsId) {

        Integer count = customerReturnListGoodsRepository.getTotalByGoodsId(goodsId);

        return count == null ? 0 : count;
    }

    /**
     * 查找所有的退货商品
     * 1.根据退货单商品，找到的货物类型，查询对应的退货商品
     * 2.根据名称或代码，查询对应的退货商品
     * 3.根据退货单，找到退货单号，查询对应的退货商品
     * @param customerReturnListGoods
     * @return
     */
    @Override
    public List<CustomerReturnListGoods> list(CustomerReturnListGoods customerReturnListGoods) {
        return customerReturnListGoodsRepository.findAll(new Specification<CustomerReturnListGoods>() {
            @Override
            public Predicate toPredicate(Root<CustomerReturnListGoods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (customerReturnListGoods != null) {
                    if (customerReturnListGoods.getType() != null
                            && customerReturnListGoods.getType().getId() != null
                            && customerReturnListGoods.getType().getId() != 1) {
                        predicate.getExpressions()
                                .add(criteriaBuilder.equal(root.get("type").get("id"),
                                        customerReturnListGoods.getType().getId()));
                    }

                    if (StringUtil.isNotEmpty(customerReturnListGoods.getCodeOrName())) {
                        predicate.getExpressions()
                                .add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"),
                                        "%" + customerReturnListGoods.getCodeOrName() + "%"),
                                        criteriaBuilder.like(root.get("name"),
                                                "%" + customerReturnListGoods.getCodeOrName() + "%")));
                    }

                    if (customerReturnListGoods.getCustomerReturnList() != null
                            && StringUtil.isNotEmpty(customerReturnListGoods.getCustomerReturnList().getCustomerReturnNumber())) {
                        predicate.getExpressions()
                                .add(criteriaBuilder.like(root.get("customerReturnList").get("customerReturnNumber"),
                                        "%" + customerReturnListGoods.getCustomerReturnList().getCustomerReturnNumber() + "%"));
                    }
                }

                return predicate;
            }
        });
    }
}
