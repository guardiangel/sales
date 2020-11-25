package com.scotia.sales.service.impl;

import com.scotia.sales.entity.Goods;
import com.scotia.sales.entity.ReturnList;
import com.scotia.sales.entity.ReturnListGoods;
import com.scotia.sales.repository.GoodsRepository;
import com.scotia.sales.repository.GoodsTypeRepository;
import com.scotia.sales.repository.ReturnListGoodsRepository;
import com.scotia.sales.repository.ReturnListRepository;
import com.scotia.sales.service.ReturnListService;
import com.scotia.sales.util.StringUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.List;

@Service("returnListService")
public class ReturnListServiceImpl implements ReturnListService {

    @Resource
    private ReturnListRepository returnListRepository;

    @Resource
    private ReturnListGoodsRepository returnListGoodsRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Override
    public ReturnList findById(Integer id) {
        return returnListRepository.findById(id).orElse(new ReturnList());
    }

    @Override
    public String getTodayMaxReturnNumber() {
        return returnListRepository.getTodayMaxReturnNumber();
    }

    @Override
    @Transactional
    public void save(ReturnList returnList, List<ReturnListGoods> returnListGoodsList) {

        returnListGoodsList.forEach(returnListGoods -> {
            returnListGoods.setType(goodsTypeRepository.findById(returnListGoods.getTypeId()).orElse(null));
            returnListGoods.setReturnList(returnList);
            //保存退货单商品
            returnListGoodsRepository.save(returnListGoods);
            //修改商品库存
            Goods goods = goodsRepository.findById(returnListGoods.getGoodsId()).orElse(null);
            if (goods != null) {
                //退货之后，库存增加
                goods.setInventoryQuantity(goods.getInventoryQuantity() + returnListGoods.getNum());
                goods.setState(2);
                goodsRepository.save(goods);
            }
        });

        returnListRepository.save(returnList);
    }

    @Override
    public List<ReturnList> list(ReturnList returnList, Sort.Direction direction, String... properties) {

        return returnListRepository.findAll((Specification<ReturnList>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (returnList != null) {
                if (returnList.getSupplier() != null && returnList.getSupplier().getId() != null) {
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("supplier").get("id"),
                            returnList.getSupplier().getId()));
                }
                if (StringUtil.isNotEmpty(returnList.getReturnNumber())) {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("returnNumber"),
                            "%" + returnList.getReturnNumber().trim() + "%"));
                }
                if (returnList.getState() != null) {
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("state"),
                            returnList.getState()));
                }
                if (returnList.getbReturnDate() != null) {
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("returnDate"),
                            returnList.getbReturnDate()));
                }
                if (returnList.geteReturnDate() != null) {
                    predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("returnDate"),
                            returnList.geteReturnDate()));
                }
            }


            return predicate;
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        returnListGoodsRepository.deleteByReturnListId(id);
        returnListRepository.deleteById(id);
    }

    @Override
    public void update(ReturnList returnList) {
        returnListRepository.save(returnList);
    }
}
