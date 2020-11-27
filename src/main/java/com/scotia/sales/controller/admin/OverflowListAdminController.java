package com.scotia.sales.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.*;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.OverflowListGoodsService;
import com.scotia.sales.service.OverflowListService;
import com.scotia.sales.service.UserService;
import com.scotia.sales.util.DateUtil;
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
 * @author Felix
 * 商品报溢Controller
 */
@RestController
@RequestMapping("/admin/overflowList")
public class OverflowListAdminController {

    @Resource
    private OverflowListService overflowListService;

    @Resource
    private OverflowListGoodsService overflowListGoodsService;

    @Resource
    private LogService logService;

    @Resource
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @ResponseBody
    @RequestMapping("/getOverflowNumber")
    @RequiresPermissions(value = {"商品报溢"})
    public String getOverflowNumber(String type) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ConstantParam.OVERFLOW_LIST_PREFIX);
        stringBuilder.append(DateUtil.getCurrentDateStr());
        String overflowNum = overflowListService.getTodayMaxOverflowNumber();
        if (overflowNum != null) {
            stringBuilder.append(StringUtil.formatCode(overflowNum));
        } else {
            stringBuilder.append(ConstantParam.OVERFLOW_LIST_CODE_DEFAULT);
        }

        return stringBuilder.toString();
    }

    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions(value = {"商品报溢"})
    public Map<String, Object> save(OverflowList overflowList, String goodsJson) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        overflowList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal()));
        Gson gson = new Gson();
        List<OverflowListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<OverflowListGoods>>() {
        }.getType());
        overflowListService.save(overflowList, plgList);
        logService.save(new Log(Log.ADD_ACTION, "商品报溢，添加报溢单"));
        resultMap.put("success", true);

        return resultMap;
    }

}
