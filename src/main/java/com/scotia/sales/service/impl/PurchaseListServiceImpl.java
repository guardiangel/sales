package com.scotia.sales.service.impl;

import com.scotia.sales.entity.Goods;
import com.scotia.sales.entity.GoodsType;
import com.scotia.sales.entity.PurchaseList;
import com.scotia.sales.entity.PurchaseListGoods;
import com.scotia.sales.repository.GoodsRepository;
import com.scotia.sales.repository.GoodsTypeRepository;
import com.scotia.sales.repository.PurchaseListGoodsRepository;
import com.scotia.sales.repository.PurchaseListRepository;
import com.scotia.sales.service.PurchaseListService;
import com.scotia.sales.util.MathUtil;
import com.scotia.sales.util.StringUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.List;

@Service("purchaseListService")
public class PurchaseListServiceImpl implements PurchaseListService {

    @Resource
    private PurchaseListRepository purchaseListRepository;

    @Resource
    private PurchaseListGoodsRepository purchaseListGoodsRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Override
    public PurchaseList findById(Integer id) {
        return purchaseListRepository.findById(id).orElse(new PurchaseList());
    }

    @Override
    public String getTodayMaxPurchaseNumber() {
        return purchaseListRepository.getTodayMaxPurchaseNumber();
    }

    @Override
    @Transactional
    public void save(PurchaseList purchaseList, List<PurchaseListGoods> purchaseListGoodsList) {

        //注意在方法上添加事物，否则会报object references an unsaved transient instance错误。

        // 保存每个进货单商品
        for (PurchaseListGoods purchaseListGoods : purchaseListGoodsList) {
            purchaseListGoods.setType(goodsTypeRepository.findById(purchaseListGoods.getTypeId()).orElse(null)); // 设置类别
           // purchaseListGoods.setPurchaseList(p); // 设置采购单
            purchaseListGoods.setPurchaseList(purchaseList); // 设置采购单
            purchaseListGoodsRepository.save(purchaseListGoods);
            // 修改商品库存 和 成本均价 以及上次进价
            Goods goods = goodsRepository.findById(purchaseListGoods.getGoodsId()).orElse(null);
            // 计算成本均价
            float avePurchasingPrice = (goods.getPurchasingPrice() * goods.getInventoryQuantity() + purchaseListGoods.getPrice() * purchaseListGoods.getNum()) / (goods.getInventoryQuantity() + purchaseListGoods.getNum());
            goods.setPurchasingPrice(MathUtil.format2Bit(avePurchasingPrice));
            goods.setInventoryQuantity(goods.getInventoryQuantity() + purchaseListGoods.getNum());
            goods.setLastPurchasingPrice(purchaseListGoods.getPrice());
            goods.setState(2);
            goodsRepository.save(goods);
        }

            purchaseListRepository.save(purchaseList); // 保存进货单

    }


    @Override
    public List<PurchaseList> list(PurchaseList purchaseList, Sort.Direction direction, String... properties) {
        return purchaseListRepository.findAll((Specification<PurchaseList>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (purchaseList != null) {
                if (purchaseList.getSupplier() != null && purchaseList.getSupplier().getId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("supplier").get("id"), purchaseList.getSupplier().getId()));
                }
                if (StringUtil.isNotEmpty(purchaseList.getPurchaseNumber())) {
                    predicate.getExpressions().add(cb.like(root.get("purchaseNumber"), "%" + purchaseList.getPurchaseNumber().trim() + "%"));
                }
                if (purchaseList.getState() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("state"), purchaseList.getState()));
                }
                if (purchaseList.getbPurchaseDate() != null) {
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("purchaseDate"), purchaseList.getbPurchaseDate()));
                }
                if (purchaseList.getePurchaseDate() != null) {
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("purchaseDate"), purchaseList.getePurchaseDate()));
                }
            }
            return predicate;
        }, Sort.by(direction, properties));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        purchaseListGoodsRepository.deleteByPurchaseListId(id);
        purchaseListRepository.deleteById(id);
    }

    @Override
    public void update(PurchaseList purchaseList) {
        purchaseListRepository.save(purchaseList);
    }
}
