package com.scotia.sales.controller.admin;

import com.scotia.sales.service.LogService;
import com.scotia.sales.service.PurchaseListGoodsService;
import com.scotia.sales.service.PurchaseListService;
import com.scotia.sales.service.UserService;
import com.scotia.sales.util.DateUtil;
import com.scotia.sales.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 进货单Controller
 */
@Controller
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


    @ResponseBody
    @RequestMapping("/getPurchaseNumber")
    @RequiresPermissions(value = {"进货入库"})
    public String genBillCode(String type) throws Exception{
        StringBuffer biilCodeStr=new StringBuffer();
        biilCodeStr.append("JH");
        biilCodeStr.append(DateUtil.getCurrentDateStr()); // 拼接当前日期
        String purchaseNumber = purchaseListService.getTodayMaxPurchaseNumber();
        if(purchaseNumber!=null){
            biilCodeStr.append(StringUtil.formatCode(purchaseNumber));
        }else{
            biilCodeStr.append("0001");
        }
        return biilCodeStr.toString();
    }




}
