package com.scotia.sales.service.impl;

import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.DamageList;
import com.scotia.sales.entity.DamageListGoods;
import com.scotia.sales.entity.Goods;
import com.scotia.sales.repository.DamageListGoodsRepository;
import com.scotia.sales.repository.DamageListRepository;
import com.scotia.sales.repository.GoodsRepository;
import com.scotia.sales.repository.GoodsTypeRepository;
import com.scotia.sales.service.DamageListService;
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
@Service("damageListService")
public class DamageListServiceImpl implements DamageListService {

    @Resource
    private DamageListRepository damageListRepository;

    @Resource
    private DamageListGoodsRepository damageListGoodsRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Override
    public DamageList findById(Integer id) {
        return damageListRepository.findById(id).orElse(null);
    }

    @Override
    public String getTodayMaxDamageNumber() {
        return damageListRepository.getTodayMaxDamageNumber();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(DamageList damageList, List<DamageListGoods> damageListGoodsList) {
        // 保存每个报损单商品
        // 修改商品库存
        damageListGoodsList.forEach(damageListGoods -> {
            damageListGoods.setType(goodsTypeRepository
                    .findById(damageListGoods.getTypeId()).orElse(null)); // 设置类别
            damageListGoods.setDamageList(damageList); // 设置采购单
            damageListGoodsRepository.save(damageListGoods);
            Goods goods = goodsRepository.findById(damageListGoods.getGoodsId()).orElse(null);
            if (goods != null) {
                goods.setInventoryQuantity(goods.getInventoryQuantity() - damageListGoods.getNum());
                goods.setState(ConstantParam.GOODS_STATE_INOROUT);
                goodsRepository.save(goods);
            }
        });
        damageListRepository.save(damageList); // 保存报损单
    }

    @Override
    public List<DamageList> list(DamageList damageList, Sort.Direction direction, String... properties) {
        return damageListRepository.findAll((Specification<DamageList>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (damageList != null) {
                if (damageList.getbDamageDate() != null) {
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("damageDate"), damageList.getbDamageDate()));
                }
                if (damageList.geteDamageDate() != null) {
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("damageDate"), damageList.geteDamageDate()));
                }
            }
            return predicate;
        }, Sort.by(direction, properties));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(Integer id) {
        damageListGoodsRepository.deleteByDamageListId(id);
        damageListRepository.deleteById(id);
    }
}
