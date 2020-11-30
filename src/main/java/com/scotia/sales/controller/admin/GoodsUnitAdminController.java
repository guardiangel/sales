package com.scotia.sales.controller.admin;

import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.GoodsType;
import com.scotia.sales.entity.GoodsUnit;
import com.scotia.sales.entity.Log;
import com.scotia.sales.service.GoodsUnitService;
import com.scotia.sales.service.LogService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Felix
 */
@RestController
@RequestMapping("/admin/goodsUnit")
public class GoodsUnitAdminController {

    @Resource
    private GoodsUnitService goodsUnitService;

    @Resource
    private LogService logService;

    @ResponseBody
    @RequestMapping("/comboList")
    @RequiresPermissions(value = {"商品管理"}, logical = Logical.OR)
    public List<GoodsUnit> comboList() throws Exception {
        List<GoodsUnit> result = goodsUnitService.listAll();
        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"商品管理"}, logical = Logical.OR)
    public Map<String, Object> delete(Integer id) {

        Map<String, Object> resultMap = new HashMap<>();
        logService.save(new Log(Log.DELETE_ACTION, "删除商品单元信息" + goodsUnitService.findById(id)));
        goodsUnitService.delete(id);
        resultMap.put("success", true);

        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions(value = {"商品管理"}, logical = Logical.OR)
    public Map<String, Object> save(GoodsUnit goodsUnit) {

        Map<String, Object> resultMap = new HashMap<>();
        goodsUnitService.save(goodsUnit);
        logService.save(new Log(Log.ADD_ACTION, "保存商品单元信息" + goodsUnit));
        resultMap.put("success", true);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/listAll")
    @RequiresPermissions(value = {"商品管理"}, logical = Logical.OR)
    public Map<String, Object> listAll() {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", goodsUnitService.listAll());
        logService.save(new Log(Log.SEARCH_ACTION, "查询商品单位信息")); // 写入日志
        return resultMap;
    }


}
