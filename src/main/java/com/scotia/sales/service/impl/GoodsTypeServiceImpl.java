package com.scotia.sales.service.impl;

import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.GoodsType;
import com.scotia.sales.repository.GoodsTypeRepository;
import com.scotia.sales.service.GoodsTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author
 *      Felix
 */
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

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveCurrentGoodsTypeAndParentGoodsType(String name, Integer parentId) {
        GoodsType goodsType = new GoodsType();
        goodsType.setName(name);
        goodsType.setpId(parentId);
        goodsType.setIcon(ConstantParam.GOODSTYPE_ICON_FOLDER);
        goodsType.setState(ConstantParam.GOODSTYPE_STATE_LEAF);//当前节点为叶子节点
        goodsTypeRepository.save(goodsType);

        //把父节点的state设置为1，无论原来父节点的state是否为1，均重新设置一次
        GoodsType parentGoodsType = goodsTypeRepository.findById(parentId).orElse(null);
        if (parentGoodsType != null) {
            parentGoodsType.setState(ConstantParam.GOODSTYPE_STATE_NOT_LEAF);
            goodsTypeRepository.save(parentGoodsType);
        }
    }
}
