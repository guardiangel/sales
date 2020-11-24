package com.scotia.sales.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scotia.sales.entity.Log;
import com.scotia.sales.entity.PurchaseList;
import com.scotia.sales.entity.PurchaseListGoods;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.PurchaseListGoodsService;
import com.scotia.sales.service.PurchaseListService;
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
 * 进货单Controller
 */
@RestController
@RequestMapping("/admin/purchaseList")
public class PurchaseListAdminController {

    @Resource
    private UserService userService;

    @Resource
    private LogService logService;

    @Resource
    private PurchaseListService purchaseListService;

    @Resource
    private PurchaseListGoodsService purchaseListGoodsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }



    @ResponseBody
    @RequestMapping("/getPurchaseNumber")
    @RequiresPermissions(value = {"进货入库"})
    public String genBillCode(String type) throws Exception {
        StringBuffer biilCodeStr = new StringBuffer();
        biilCodeStr.append("JH");
        biilCodeStr.append(DateUtil.getCurrentDateStr()); // 拼接当前日期
        String purchaseNumber = purchaseListService.getTodayMaxPurchaseNumber();
        if (purchaseNumber != null) {
            biilCodeStr.append(StringUtil.formatCode(purchaseNumber));
        } else {
            biilCodeStr.append("0001");
        }
        return biilCodeStr.toString();
    }

    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions(value = {"进货入库"})
    public Map<String,Object> save(PurchaseList purchaseList,String goodsJson)throws Exception{

        Map<String, Object> resultMap = new HashMap<>();

        purchaseList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal()));
        Gson gson = new Gson();
        System.err.println("goodsJson=" + goodsJson);
        List<PurchaseListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<PurchaseListGoods>>() {
        }.getType());
        purchaseListService.save(purchaseList, plgList);
        logService.save(new Log(Log.ADD_ACTION,"添加进货单"));
        resultMap.put("success", true);

        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/listGoods")
    @RequiresPermissions(value = {"进货单据查询"})
    public Map<String,Object> listGoods(Integer purchaseListId)throws Exception{

        if (purchaseListId == null) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>();
        List<PurchaseListGoods> purchaseListGoods = purchaseListGoodsService.listByPurchaseListId(purchaseListId);
        resultMap.put("rows", purchaseListGoods);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions(value = {"进货单据查询"})
    public Map<String,Object> list(PurchaseList purchaseList)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        List<PurchaseList> purchaseLists = purchaseListService.list(purchaseList, Sort.Direction.DESC, "purchaseDate");
        resultMap.put("rows", purchaseLists);
        return resultMap;
    }




}
