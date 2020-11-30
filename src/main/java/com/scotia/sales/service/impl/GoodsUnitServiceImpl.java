package com.scotia.sales.service.impl;

import com.scotia.sales.entity.GoodsUnit;
import com.scotia.sales.repository.GoodsUnitRepository;
import com.scotia.sales.service.GoodsUnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Felix
 * 商品单位操作接口
 */
@Service("goodsUnitService")
public class GoodsUnitServiceImpl implements GoodsUnitService {

    @Resource
    private GoodsUnitRepository goodsUnitRepository;

    @Override
    public GoodsUnit findById(Integer id) {
        return goodsUnitRepository.findById(id).orElse(null);
    }

    @Override
    public List<GoodsUnit> listAll() {
        return goodsUnitRepository.findAll();
    }

    @Override
    public void save(GoodsUnit goodsUnit) {
        goodsUnitRepository.save(goodsUnit);
    }

    @Override
    public void delete(Integer id) {
        goodsUnitRepository.deleteById(id);
    }
}
