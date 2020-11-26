package com.scotia.sales.service.impl;

import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.Goods;
import com.scotia.sales.entity.SaleList;
import com.scotia.sales.entity.SaleListGoods;
import com.scotia.sales.repository.GoodsRepository;
import com.scotia.sales.repository.GoodsTypeRepository;
import com.scotia.sales.repository.SaleListGoodsRepository;
import com.scotia.sales.repository.SaleListRepository;
import com.scotia.sales.service.SaleListService;
import com.scotia.sales.util.StringUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Felix
 */
@Service("saleListService")
public class SaleListServiceImpl implements SaleListService {

    @Resource
    private SaleListRepository saleListRepository;

    @Resource
    private SaleListGoodsRepository saleListGoodsRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Override
    public SaleList findById(Integer id) {
        return saleListRepository.findById(id).orElse(null);
    }

    @Override
    public String getTodayMaxSaleNumber() {
        return saleListRepository.getTodayMaxSaleNumber();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(SaleList saleList, List<SaleListGoods> saleListGoodsList) {
        saleListGoodsList.forEach(saleListGoods -> {
            saleListGoods.setType(goodsTypeRepository.findById(saleListGoods.getTypeId()).orElse(null));
            saleListGoods.setSaleList(saleList);
            saleListGoodsRepository.save(saleListGoods);
            //修改商品库存
            Goods goods = goodsRepository.findById(saleListGoods.getGoodsId()).orElse(null);
            if (goods != null) {
                goods.setInventoryQuantity(goods.getInventoryQuantity() - saleListGoods.getNum());
                goods.setState(ConstantParam.GOODS_STATE_INOROUT);
                goodsRepository.save(goods);
            }
        });
        //保存销售单
        saleListRepository.save(saleList);
    }

    @Override
    public List<SaleList> list(SaleList saleList, Sort.Direction direction, String... properties) {
        return saleListRepository.findAll((Specification<SaleList>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (saleList != null) {
                if (saleList.getCustomer() != null && saleList.getCustomer().getId() != null) {
                    predicate.getExpressions().add(
                            criteriaBuilder.equal(root.get("customer").get("id"), saleList.getCustomer().getId())
                    );
                }

                if (StringUtil.isNotEmpty(saleList.getSaleNumber())) {
                    predicate.getExpressions().add(
                            criteriaBuilder.like(root.get("saleNumber"), "%" + saleList.getSaleNumber().trim() + "%")
                    );
                }

                if (saleList.getState() != null) {
                    predicate.getExpressions().add(
                            criteriaBuilder.equal(root.get("state"), saleList.getState())
                    );
                }

                if (saleList.getbSaleDate() != null) {
                    predicate.getExpressions().add(
                            criteriaBuilder.greaterThanOrEqualTo(root.get("saleDate"), saleList.getbSaleDate())
                    );
                }
                if (saleList.geteSaleDate() != null) {
                    predicate.getExpressions().add(
                            criteriaBuilder.lessThanOrEqualTo(root.get("saleDate"), saleList.geteSaleDate())
                    );
                }
            }
            return predicate;
        }, Sort.by(direction, properties));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(Integer saleListId) {
        saleListGoodsRepository.deleteBySaleListId(saleListId);
        saleListRepository.deleteById(saleListId);
    }

    @Override
    public void update(SaleList saleList) {
        saleListRepository.save(saleList);
    }

    @Override
    public List<Object> countSaleByDay(String begin, String end) {
        return saleListRepository.countSaleByDay(begin, end);
    }

    @Override
    public List<Object> countSaleByMonth(String begin, String end) {
        return saleListRepository.countSaleByMonth(begin, end);
    }
}
