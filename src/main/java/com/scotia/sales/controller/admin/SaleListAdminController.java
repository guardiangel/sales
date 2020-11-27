package com.scotia.sales.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.Log;
import com.scotia.sales.entity.SaleList;
import com.scotia.sales.entity.SaleListGoods;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.SaleListGoodsService;
import com.scotia.sales.service.SaleListService;
import com.scotia.sales.service.UserService;
import com.scotia.sales.util.DateUtil;
import com.scotia.sales.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Felix
 */
@RestController
@RequestMapping("/admin/saleList")
public class SaleListAdminController {

    @Resource
    private UserService userService;

    @Resource
    private LogService logService;

    @Resource
    private SaleListService saleListService;

    @Resource
    private SaleListGoodsService saleListGoodsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @ResponseBody
    @RequestMapping("/getSaleNumber")
    @RequiresPermissions(value = {"销售出库"})
    public String getSaleNumber(String type) throws Exception {
        StringBuilder stringBuilder = new StringBuilder(ConstantParam.SALE_LIST_PREFIX);
        stringBuilder.append(DateUtil.getCurrentDateStr());
        String maxNum = saleListService.getTodayMaxSaleNumber();
        if (maxNum != null) {
            stringBuilder.append(StringUtil.formatCode(maxNum));
        } else {
            stringBuilder.append(ConstantParam.SALE_LIST_CODE_DEFAULT);
        }
        return stringBuilder.toString();
    }

    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions(value = {"销售出库"})
    public Map<String, Object> save(SaleList saleList, String goodsJson) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        saleList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal()));
        Gson gson = new Gson();
        List<SaleListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<SaleListGoods>>() {
        }.getType());
        saleListService.save(saleList, plgList);
        logService.save(new Log(Log.ADD_ACTION, "添加销售单"));
        resultMap.put("success", true);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions(value = {"销售单据查询"})
    public Map<String, Object> list(SaleList saleList) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<SaleList> saleLists = saleListService.list(saleList, Sort.Direction.DESC, "saleDate");
        resultMap.put("rows", saleLists);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/listGoods")
    @RequiresPermissions(value = {"销售单据查询"})
    public Map<String, Object> listGoods(Integer saleListId) throws Exception {
        if (saleListId == null) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>();
        List<SaleListGoods> saleListGoods = saleListGoodsService.listBySaleListId(saleListId);
        resultMap.put("rows", saleListGoods);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"销售单据查询"})
    public Map<String, Object> delete(Integer id) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        saleListService.delete(id);
        resultMap.put("success", true);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions(value = {"客户统计"})
    public Map<String, Object> update(Integer id) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        SaleList saleList = saleListService.findById(id);
        saleList.setState(ConstantParam.SALELIST_STATE_PAID);
        saleListService.update(saleList);
        resultMap.put("success", true);

        return resultMap;
    }

    /**
     * 根据条件查询商品销售情况
     * @param saleList
     * @param saleListGoods
     * @return
     * @throws Exception
     */
    @RequestMapping("/listCount")
    @RequiresPermissions(value = {"商品销售统计"})
    public Map<String, Object> listCount(SaleList saleList, SaleListGoods saleListGoods) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        List<SaleList> sales = saleListService.list(saleList, Sort.Direction.DESC, "saleDate");
        sales.forEach(saleList1 -> {
            saleListGoods.setSaleList(saleList1);
            List<SaleListGoods> plgList = saleListGoodsService.list(saleListGoods);
            plgList.forEach(plg -> {
                plg.setSaleList(null);
            });
            saleList1.setSaleListGoodsList(plgList);
        });
        resultMap.put("rows", sales);

        return resultMap;
    }

}
