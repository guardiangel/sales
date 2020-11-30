package com.scotia.sales.controller.admin;

import com.scotia.sales.entity.Log;
import com.scotia.sales.entity.SaleList;
import com.scotia.sales.service.*;
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
import java.util.Map;

/**
 * @author Felix
 */
@RestController
@RequestMapping("/admin/role")
public class RoleAdminController {

    @Resource
    private LogService logService;

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private MenuService menuService;

    @Resource
    private RoleMenuService roleMenuService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @ResponseBody
    @RequestMapping("/listAll")
    @RequiresPermissions(value = {"客户管理"})
    public Map<String, Object> listAll() throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", roleService.listAll());
        logService.save(new Log(Log.SEARCH_ACTION,"查询所有角色信息"));

        return resultMap;
    }

}
