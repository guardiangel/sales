package com.scotia.sales.controller.admin;

import com.scotia.sales.entity.Log;
import com.scotia.sales.entity.Role;
import com.scotia.sales.entity.User;
import com.scotia.sales.entity.UserRole;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.RoleService;
import com.scotia.sales.service.UserRoleService;
import com.scotia.sales.service.UserService;
import com.scotia.sales.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 *      Felix
 * 后台用户管理Crontoller
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController {

    @Resource
    private LogService logService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @ResponseBody
    @PostMapping("/modifyPassword")
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
    /**
     * 添加或者修改用户信息
     * @param user
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions(value = { "用户管理" })
    public Map<String,Object> save(User user)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        if(user.getId()==null){
            if(userService.findByUserName(user.getUserName())!=null){
                resultMap.put("success", false);
                resultMap.put("errorInfo", "用户名已经存在!");
                return resultMap;
            }
        }
        if(user.getId()!=null){
            logService.save(new Log(Log.UPDATE_ACTION,"更新用户信息"+user));
        }else{
            logService.save(new Log(Log.ADD_ACTION,"添加用户信息"+user));
        }
        userService.save(user);
        resultMap.put("success", true);
        return resultMap;
    }


    /**
     * 删除用户信息
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions(value = { "用户管理" })
    public Map<String,Object> delete(Integer id)throws Exception{
        logService.save(new Log(Log.DELETE_ACTION,"删除用户信息"+userService.findByUserId(id)));  // 写入日志
        Map<String, Object> resultMap = new HashMap<>();
        userService.delete(id);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 分页查询用户信息
     * @param user
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions(value = { "用户管理" })
    public Map<String,Object> list(User user, @RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer rows)throws Exception{
        List<User> userList=userService.list(user, page, rows, Sort.Direction.ASC, "id");
        for(User u:userList){
            List<Role> roleList=roleService.findByUserId(u.getId());
            StringBuffer sb=new StringBuffer();
            for(Role r:roleList){
                sb.append(","+r.getName());
            }
            u.setRoles(sb.toString().replaceFirst(",", ""));
        }
        Long total=userService.getCount(user);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", userList);
        resultMap.put("total", total);
        logService.save(new Log(Log.SEARCH_ACTION,"查询用户信息")); // 写入日志
        return resultMap;
    }

    /**
     * 保存用户角色设置
     * @param roleIds
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/saveRoleSet")
    @RequiresPermissions(value = { "用户管理" })
    public Map<String,Object> saveRoleSet(String roleIds,Integer userId)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        userRoleService.deleteByUserId(userId);  // 根据用户id删除所有用户角色关联实体
        if(StringUtil.isNotEmpty(roleIds)){
            String idsStr[]=roleIds.split(",");
            for(int i=0;i<idsStr.length;i++){ // 然后添加所有用户角色关联实体
                UserRole userRole=new UserRole();
                userRole.setUser(userService.findByUserId(userId));
                userRole.setRole(roleService.findById(Integer.parseInt(idsStr[i])));
                userRoleService.save(userRole);
            }
        }
        resultMap.put("success", true);
        logService.save(new Log(Log.UPDATE_ACTION,"保存用户角色设置"));
        return resultMap;
    }
}
