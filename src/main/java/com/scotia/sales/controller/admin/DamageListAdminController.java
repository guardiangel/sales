package com.scotia.sales.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.*;
import com.scotia.sales.service.DamageListGoodsService;
import com.scotia.sales.service.DamageListService;
import com.scotia.sales.service.LogService;
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
@RequestMapping("/admin/damageList")
public class DamageListAdminController {
    @Resource
    private DamageListService damageListService;

    @Resource
    private DamageListGoodsService damageListGoodsService;

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
    @RequestMapping("/getDamageNumber")
    @RequiresPermissions(value = {"商品报损"})
    public String getDamageNumber(String type) throws Exception {

        StringBuilder billCodeStr = new StringBuilder();
        billCodeStr.append(ConstantParam.DAMAGE_LIST_PREFIX);
        billCodeStr.append(DateUtil.getCurrentDateStr());
        String damageNumber = damageListService.getTodayMaxDamageNumber();
        if (damageNumber != null) {
            billCodeStr.append(StringUtil.formatCode(damageNumber));
        } else {
            billCodeStr.append(ConstantParam.DAMAGE_LIST_CODE_DEFAULT);
        }

        return billCodeStr.toString();
    }

    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions(value = {"商品报损"})
    public Map<String, Object> save(DamageList damageList, String goodsJson) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        damageList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal()));
        Gson gson = new Gson();
        List<DamageListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<DamageListGoods>>() {
        }.getType());
        damageListService.save(damageList, plgList);
        logService.save(new Log(Log.ADD_ACTION, "商品报损，添加报损单"));
        resultMap.put("success", true);

        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions(value = {"报损报溢查询"})
    public Map<String, Object> list(DamageList damageList) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        List<DamageList> damageListList =
                damageListService.list(damageList, Sort.Direction.DESC, "damageDate");
        resultMap.put("rows", damageListList);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/listGoods")
    @RequiresPermissions(value = {"报损报溢查询"})
    public Map<String, Object> listGoods(Integer damageListId) throws Exception {

        if (damageListId == null) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>();
        List<DamageListGoods> damageListGoods = damageListGoodsService.listByDamageListId(damageListId);
        resultMap.put("rows", damageListGoods);

        return resultMap;
    }


}
