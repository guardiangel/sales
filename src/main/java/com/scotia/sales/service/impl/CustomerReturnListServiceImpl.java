package com.scotia.sales.service.impl;

import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.CustomerReturnList;
import com.scotia.sales.entity.CustomerReturnListGoods;
import com.scotia.sales.entity.Goods;
import com.scotia.sales.repository.CustomerReturnListGoodsRepository;
import com.scotia.sales.repository.CustomerReturnListRepository;
import com.scotia.sales.repository.GoodsRepository;
import com.scotia.sales.repository.GoodsTypeRepository;
import com.scotia.sales.service.CustomerReturnListService;
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
@Service("customerReturnListService")
public class CustomerReturnListServiceImpl implements CustomerReturnListService {

    @Resource
    private CustomerReturnListRepository customerReturnListRepository;

    @Resource
    private CustomerReturnListGoodsRepository customerReturnListGoodsRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Override
    public CustomerReturnList findById(Integer id) {
        return customerReturnListRepository.findById(id).orElse(null);
    }

    @Override
    public String getTodayMaxCustomerReturnNumber() {
        return customerReturnListRepository.getTodayMaxCustomerReturnNumber();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(CustomerReturnList customerReturnList, List<CustomerReturnListGoods> customerReturnListGoodsList) {
        // 保存每个销售单商品，并修改商品库存
        customerReturnListGoodsList.forEach(customerReturnListGoods -> {
            customerReturnListGoods.setType(goodsTypeRepository.findById(customerReturnListGoods.getTypeId()).orElse(null)); // 设置类别
            // 设置采购单
            customerReturnListGoods.setCustomerReturnList(customerReturnList);
            customerReturnListGoodsRepository.save(customerReturnListGoods);
            Goods goods = goodsRepository.findById(customerReturnListGoods.getGoodsId()).orElse(null);
            if (goods != null) {
                goods.setInventoryQuantity(goods.getInventoryQuantity() + customerReturnListGoods.getNum());
                goods.setState(ConstantParam.GOODS_STATE_INOROUT);
                goodsRepository.save(goods);
            }
        });
        // 保存销售单
        customerReturnListRepository.save(customerReturnList);
    }

    @Override
    public List<CustomerReturnList> list(CustomerReturnList customerReturnList, Sort.Direction direction, String... properties) {
        return customerReturnListRepository.findAll((Specification<CustomerReturnList>) (root, query, cb) -> {
            Predicate predicate=cb.conjunction();
            if(customerReturnList!=null){
                if(customerReturnList.getCustomer()!=null && customerReturnList.getCustomer().getId()!=null){
                    predicate.getExpressions().add(cb.equal(root.get("customer").get("id"), customerReturnList.getCustomer().getId()));
                }
                if(StringUtil.isNotEmpty(customerReturnList.getCustomerReturnNumber())){
                    predicate.getExpressions().add(cb.like(root.get("customerReturnNumber"), "%"+customerReturnList.getCustomerReturnNumber().trim()+"%"));
                }
                if(customerReturnList.getState()!=null){
                    predicate.getExpressions().add(cb.equal(root.get("state"), customerReturnList.getState()));
                }
                if(customerReturnList.getbCustomerReturnDate()!=null){
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("customerReturnDate"), customerReturnList.getbCustomerReturnDate()));
                }
                if(customerReturnList.geteCustomerReturnDate()!=null){
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("customerReturnDate"), customerReturnList.geteCustomerReturnDate()));
                }
            }
            return predicate;
        },Sort.by(direction, properties));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(Integer id) {
        customerReturnListGoodsRepository.deleteByCustomerReturnListId(id);
        customerReturnListRepository.deleteById(id);
    }

    @Override
    public void update(CustomerReturnList customerReturnList) {
        customerReturnListRepository.save(customerReturnList);
    }
}
