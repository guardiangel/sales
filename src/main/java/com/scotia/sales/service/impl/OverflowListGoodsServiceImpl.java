package com.scotia.sales.service.impl;

import com.scotia.sales.entity.OverflowListGoods;
import com.scotia.sales.repository.OverflowListGoodsRepository;
import com.scotia.sales.service.OverflowListGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Felix
 */
@Service("overflowListGoodsService")
public class OverflowListGoodsServiceImpl implements OverflowListGoodsService {

    @Resource
    private OverflowListGoodsRepository overflowListGoodsRepository;

    @Override
    public List<OverflowListGoods> listByOverflowListId(Integer overflowListId) {
        return overflowListGoodsRepository.listByOverflowListId(overflowListId);
    }

}
