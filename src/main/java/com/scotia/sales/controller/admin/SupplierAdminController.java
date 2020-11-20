package com.scotia.sales.controller.admin;

import com.scotia.sales.entity.Supplier;
import com.scotia.sales.service.SupplierService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/supplier")
public class SupplierAdminController {

    @Resource
    private SupplierService supplierService;

    @RequestMapping("/comboList")
    @RequiresPermissions(value = {"进货入库","退货出库","进货单据查询","退货单据查询"},
            logical = Logical.OR)
    public List<Supplier> comboList(String q) throws Exception {
        if (q == null) {
            q = "";
        }
        return supplierService.findByName("%"+q+"%");
    }



}
