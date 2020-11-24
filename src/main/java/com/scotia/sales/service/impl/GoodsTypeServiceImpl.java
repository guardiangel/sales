package com.scotia.sales.service.impl;

import com.scotia.sales.entity.GoodsType;
import com.scotia.sales.repository.GoodsTypeRepository;
import com.scotia.sales.service.GoodsTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService {

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Override
    public GoodsType findById(Integer id) {
        return goodsTypeRepository.findById(id).orElse(new GoodsType());
    }

    @Override
    public List<GoodsType> findByParentId(int parentId) {
        return goodsTypeRepository.findByParentId(parentId);
    }

    @Override
    public void save(GoodsType goodsType) {
        goodsTypeRepository.save(goodsType);
    }

    @Override
    public void delete(Integer id) {
        goodsTypeRepository.deleteById(id);
    }
}
