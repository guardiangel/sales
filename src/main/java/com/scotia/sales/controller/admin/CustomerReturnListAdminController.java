package com.scotia.sales.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.*;
import com.scotia.sales.service.CustomerReturnListGoodsService;
import com.scotia.sales.service.CustomerReturnListService;
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
@RequestMapping("/admin/customerReturnList")
public class CustomerReturnListAdminController {

    @Resource
    private LogService logService;

    @Resource
    private CustomerReturnListService customerReturnListService;

    @Resource
    private CustomerReturnListGoodsService customerReturnListGoodsService;

    @Resource
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @ResponseBody
    @RequestMapping("/getCustomerReturnNumber")
    @RequiresPermissions(value = {"客户退货"})
    public String genBillCode(String type) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ConstantParam.CUSTOMER_RETURN_LIST_PREFIX);
        stringBuilder.append(DateUtil.getCurrentDateStr());
        String customerReturnNumber = customerReturnListService.getTodayMaxCustomerReturnNumber();
        if (customerReturnNumber != null) {
            stringBuilder.append(StringUtil.formatCode(customerReturnNumber));
        } else {
            stringBuilder.append(ConstantParam.CUSTOMER_RETURN_LIST_CODE_DEFAULT);
        }
        return stringBuilder.toString();
    }

    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions(value = {"客户退货"})
    public Map<String, Object> save(CustomerReturnList customerReturnList, String goodsJson) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        customerReturnList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal()));

        Gson gson = new Gson();
        List<CustomerReturnListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<CustomerReturnListGoods>>() {
        }.getType());

        customerReturnListService.save(customerReturnList, plgList);

        logService.save(new Log(Log.ADD_ACTION, "添加客户退货单"));

        resultMap.put("success", true);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions(value = {"退货单据查询"})
    public Map<String, Object> list(CustomerReturnList customerReturnList) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<CustomerReturnList> customerReturnLists =
                customerReturnListService.list(customerReturnList, Sort.Direction.DESC, "customerReturnDate");
        resultMap.put("rows", customerReturnLists);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/listGoods")
    @RequiresPermissions(value = {"退货单据查询"})
    public Map<String, Object> listGoods(Integer customerReturnListId) throws Exception {
        if (customerReturnListId == null) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>();
        List<CustomerReturnListGoods> customerReturnListGoods =
                customerReturnListGoodsService.listByCustomerReturnListId(customerReturnListId);
        resultMap.put("rows", customerReturnListGoods);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"退货单据查询"})
    public Map<String, Object> delete(Integer id) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        customerReturnListService.delete(id);
        resultMap.put("success", true);
        return resultMap;
    }


}
