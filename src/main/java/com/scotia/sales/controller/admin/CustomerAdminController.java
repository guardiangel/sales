package com.scotia.sales.controller.admin;

import com.scotia.sales.entity.Customer;
import com.scotia.sales.entity.Supplier;
import com.scotia.sales.service.CustomerService;
import com.scotia.sales.service.SupplierService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 *      Felix
 */
@RestController
@RequestMapping("/admin/customer")
public class CustomerAdminController {

    @Resource
    private CustomerService customerService;

    @ResponseBody
    @RequestMapping("/comboList")
    @RequiresPermissions(value = {"销售出库","客户退货","销售单据查询","客户退货查询"},logical=Logical.OR)
    public List<Customer> comboList(String q) throws Exception {
        if (q == null) {
            q = "";
        }

        List<Customer> result = customerService.findByName("%"+q+"%");

        return result;
    }



}
