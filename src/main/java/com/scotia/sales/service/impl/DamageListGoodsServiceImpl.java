package com.scotia.sales.service.impl;

import com.scotia.sales.entity.DamageListGoods;
import com.scotia.sales.repository.DamageListGoodsRepository;
import com.scotia.sales.service.DamageListGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Felix
 */
@Service("damageListGoodsService")
public class DamageListGoodsServiceImpl implements DamageListGoodsService {

    @Resource
    private DamageListGoodsRepository damageListGoodsRepository;

    @Override
    public List<DamageListGoods> listByDamageListId(Integer damageListId) {
        return damageListGoodsRepository.listByDamageListId(damageListId);
    }

}
