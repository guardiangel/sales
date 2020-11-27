package com.scotia.sales.service.impl;

import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.Goods;
import com.scotia.sales.entity.OverflowList;
import com.scotia.sales.entity.OverflowListGoods;
import com.scotia.sales.repository.GoodsRepository;
import com.scotia.sales.repository.GoodsTypeRepository;
import com.scotia.sales.repository.OverflowListGoodsRepository;
import com.scotia.sales.repository.OverflowListRepository;
import com.scotia.sales.service.OverflowListService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Felix
 * 商品报溢Servie
 */
@Service("overflowListService")
public class OverflowListServiceImpl implements OverflowListService {

    @Resource
    private OverflowListRepository overflowListRepository;

    @Resource
    private OverflowListGoodsRepository overflowListGoodsRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Override
    public OverflowList findById(Integer id) {
        return overflowListRepository.findById(id).orElse(null);
    }

    @Override
    public String getTodayMaxOverflowNumber() {
        return overflowListRepository.getTodayMaxOverflowNumber();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(OverflowList overflowList, List<OverflowListGoods> overflowListGoodsList) {

        overflowListGoodsList.forEach(overflowListGoods -> {
            overflowListGoods.setType(goodsTypeRepository
                    .findById(overflowListGoods.getTypeId()).orElse(null));
            overflowListGoods.setOverflowList(overflowList);
            overflowListGoodsRepository.save(overflowListGoods);
            //修改库存
            Goods goods = goodsRepository.findById(overflowListGoods.getGoodsId()).orElse(null);
            if (goods != null) {
                goods.setInventoryQuantity(goods.getInventoryQuantity() + overflowListGoods.getNum());
                goods.setState(ConstantParam.GOODS_STATE_INOROUT);
                goodsRepository.save(goods);
            }
        });

        overflowListRepository.save(overflowList);
    }

    @Override
    public List<OverflowList> list(OverflowList overflowList, Sort.Direction direction, String... properties) {
        return overflowListRepository.findAll((Specification<OverflowList>) (root, query, cb) -> {
            Predicate predicate=cb.conjunction();
            if(overflowList!=null){
                if(overflowList.getbOverflowDate()!=null){
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("overflowDate"), overflowList.getbOverflowDate()));
                }
                if(overflowList.geteOverflowDate()!=null){
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("overflowDate"), overflowList.geteOverflowDate()));
                }
            }
            return predicate;
        },Sort.by(direction, properties));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(Integer id) {
        overflowListGoodsRepository.deleteByOverflowListId(id);
        overflowListRepository.deleteById(id);
    }
}
