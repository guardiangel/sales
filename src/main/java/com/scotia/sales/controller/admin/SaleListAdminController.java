package com.scotia.sales.controller.admin;

import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.SaleListGoodsService;
import com.scotia.sales.service.SaleListService;
import com.scotia.sales.service.UserService;
import com.scotia.sales.util.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @ResponseBody
    @RequestMapping("/getSaleNumber")
    @RequiresPermissions(value = {"销售出库"})
    public String getSaleNumber(String type) throws Exception {
        StringBuilder stringBuilder = new StringBuilder(ConstantParam.SALE_LIST_PREFIX);
        stringBuilder.append(DateUtil.getCurrentDateStr());
        String maxNum = saleListService.getTodayMaxSaleNumber();
        if (maxNum != null) {
            stringBuilder.append(maxNum);
        } else {
            stringBuilder.append(ConstantParam.SALE_LIST_CODE_DEFAULT);
        }
        return stringBuilder.toString();
    }



}
