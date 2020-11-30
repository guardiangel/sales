package com.scotia.sales.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scotia.sales.entity.*;
import com.scotia.sales.service.CustomerService;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.SupplierService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/admin/customer")
public class CustomerAdminController {

    @Resource
    private CustomerService customerService;

    @Resource
    private LogService logService;

    @ResponseBody
    @RequestMapping("/comboList")
    @RequiresPermissions(value = {"销售出库", "客户退货", "销售单据查询", "客户退货查询"}, logical = Logical.OR)
    public List<Customer> comboList(String q) throws Exception {
        if (q == null) {
            q = "";
        }

        List<Customer> result = customerService.findByName("%" + q + "%");

        return result;
    }

    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions(value = {"客户管理"})
    public Map<String, Object> save(Customer customer) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        logService.save(customer.getId() == null ? new Log(Log.ADD_ACTION, "添加客户信息" + customer)
                : new Log(Log.UPDATE_ACTION, "更新客户信息" + customer));
        customerService.save(customer);
        resultMap.put("success", true);

        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions(value = {"客户管理"})
    public Map<String, Object> list(Customer customer,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "rows", required = false) Integer rows) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        List<Customer> customerList = customerService.list(customer, page, rows, Sort.Direction.ASC, "id");
        Long total = customerService.getCount(customer);
        logService.save(new Log(Log.SEARCH_ACTION,"查询客户信息"));
        resultMap.put("rows", customerList);
        resultMap.put("total", total);

        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"客户管理"})
    public Map<String, Object> delete(String ids) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        logService.save(new Log(Log.DELETE_ACTION, "删除用户信息"));
        customerService.deleteByIds(ids);
        resultMap.put("success", true);

        return resultMap;
    }

}
