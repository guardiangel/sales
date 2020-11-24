package com.scotia.sales.controller.admin;

import com.scotia.sales.entity.Log;
import com.scotia.sales.entity.User;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台用户管理Crontoller
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController {

    @Resource
    private LogService logService;

    @Resource
    private UserService userService;

    @ResponseBody
    @GetMapping("/modifyPassword")
    @RequiresPermissions(value = {"修改密码"})
    public Map<String, Object> modifyPassword(Integer id, String newPassword, HttpSession session) throws Exception {
        User currentUser = (User) session.getAttribute("currentUser");
        User user = userService.findByUserId(currentUser.getId());
        user.setPassword(newPassword);
        userService.save(user);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        logService.save(new Log(Log.UPDATE_ACTION, "修改密码")); // 写入日志
        return map;
    }

    @GetMapping("/logout")
    @RequiresPermissions(value = {"安全退出"})
    public String logout() throws Exception {
        logService.save(new Log(Log.LOGOUT_ACTION, "用户注销"));//保存日志
        SecurityUtils.getSubject().logout();
        return "redirect:/login.html";
    }
}
