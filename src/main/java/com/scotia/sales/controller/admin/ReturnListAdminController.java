package com.scotia.sales.controller.admin;

import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.ReturnListGoodsService;
import com.scotia.sales.service.ReturnListService;
import com.scotia.sales.service.UserService;
import com.scotia.sales.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 退货单Controller
 */
@RestController
@RequestMapping("/admin/returnList")
public class ReturnListAdminController {
    @Resource
    private LogService logService;
    @Resource
    private UserService userService;

    @Resource
    private ReturnListService returnListService;

    @Resource
    private ReturnListGoodsService returnListGoodsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }


    @ResponseBody
    @RequestMapping("/getReturnNumber")
    @RequiresPermissions(value = {"退货出库"})
    public String getReturnNumber(String type) throws Exception {

        StringBuffer billCodeStr = new StringBuffer();
        billCodeStr.append(ConstantParam.RETURN_LIST_PREFIX);
        String returnNumber = returnListService.getTodayMaxReturnNumber();
        if (returnNumber != null) {
            billCodeStr.append(StringUtil.formatCode(returnNumber));
        } else {
            billCodeStr.append(ConstantParam.RETURN_LIST_CODE_DEFAULT);
        }

        return billCodeStr.toString();
    }
}
