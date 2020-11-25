package com.scotia.sales.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.Log;
import com.scotia.sales.entity.ReturnList;
import com.scotia.sales.entity.ReturnListGoods;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.ReturnListGoodsService;
import com.scotia.sales.service.ReturnListService;
import com.scotia.sales.service.UserService;
import com.scotia.sales.util.StringUtil;
import org.apache.shiro.SecurityUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions(value = {"退货出库"})
    public Map<String, Object> save(ReturnList returnList, String goodsJson) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        returnList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal()));
        Gson gson = new Gson();
        List<ReturnListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<ReturnListGoods>>() {
        }.getType());
        returnListService.save(returnList, plgList);
        logService.save(new Log(Log.ADD_ACTION, "添加退货单"));
        resultMap.put("success", true);

        return resultMap;
    }
}
