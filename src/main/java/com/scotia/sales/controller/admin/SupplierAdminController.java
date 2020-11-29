package com.scotia.sales.controller.admin;

import com.scotia.sales.entity.Log;
import com.scotia.sales.entity.Supplier;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.SupplierService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Felix
 */
@RestController
@RequestMapping("/admin/supplier")
public class SupplierAdminController {

    @Resource
    private SupplierService supplierService;

    @Resource
    private LogService logService;

    @ResponseBody
    @RequestMapping("/comboList")
    @RequiresPermissions(value = {"进货入库", "退货出库", "进货单据查询", "退货单据查询"}, logical = Logical.OR)
    public List<Supplier> comboList(String q) throws Exception {
        if (q == null) {
            q = "";
        }

        List<Supplier> result = supplierService.findByName("%" + q + "%");
        return result;
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions(value = {"供应商管理"}, logical = Logical.OR)
    public Map<String, Object> list(Supplier supplier, @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "rows", required = false) Integer rows) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<Supplier> suppliers = supplierService.list(supplier, page, rows, Sort.Direction.ASC, "id");
        Long total = supplierService.getCount(supplier);
        resultMap.put("rows", suppliers);
        resultMap.put("total", total);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions(value = {"供应商管理"}, logical = Logical.OR)
    public Map<String, Object> save(Supplier supplier) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        logService.save(supplier.getId() == null ? new Log(Log.ADD_ACTION, "添加供应商信息" + supplier)
                : new Log(Log.UPDATE_ACTION, "更新供应商信息" + supplier));
        supplierService.save(supplier);
        resultMap.put("success", true);

        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"供应商管理"}, logical = Logical.OR)
    public Map<String, Object> delete(String ids) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        supplierService.deleteByIds(ids);
        resultMap.put("success", true);

        return resultMap;
    }


}
