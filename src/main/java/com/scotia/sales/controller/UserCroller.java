package com.scotia.sales.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.Log;
import com.scotia.sales.entity.Menu;
import com.scotia.sales.entity.Role;
import com.scotia.sales.entity.User;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.MenuService;
import com.scotia.sales.service.RoleService;
import com.scotia.sales.service.UserService;
import com.scotia.sales.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 *      Felix
 * 用户登录控制器，点击提交会到此控制器
 */
@Controller
@RequestMapping("/user")
public class UserCroller {

    private Logger logger = LoggerFactory.getLogger(UserCroller.class);

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;

    @Resource
    private LogService logService;

    /**
     * 用户登录请求
     *
     * @param imageCode
     * @param user
     * @param bindingResult
     * @param session
     * @return <p>
     * @ResponseBody 的作用其实是将java对象转为json格式的数据。
     * @ResponseBody 注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，
     * 写入到response对象的body区，通常用来返回JSON数据或者是XML数据。
     * 注意：在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据。
     * @ResponseBody是作用在方法上的，@ResponseBody 表示该方法的返回结果直接写入 HTTP response body 中，一般在异步获取数据时使用【也就是AJAX】。
     * 注意：在使用 @RequestMapping后，返回值通常解析为跳转路径，但是加上
     * @ResponseBody 后返回结果不会被解析为跳转路径，而是直接写入 HTTP response body 中。 比如异步获取 json 数据，加上 @ResponseBody 后，会直接返回 json 数据。@RequestBody 将 HTTP 请求正文插入方法中，使用适合的 HttpMessageConverter 将请求体写入某个对象。
     * </p>
     * <p>
     * @PostMapping("/login") is the same as
     * @RequestMapping(value="/login", method = RequestMethod.POST)
     * </p>
     * <p>
     * @Valid 用于验证注解是否符合要求，直接加在变量user之前，在变量中添加验证信息的要求，当不符合要求时就会在方法中返回message 的错误提示信息。
     * @Valid 和 BindingResult 是一 一对应的, BindingResult 一般在controller中使用, 作用：用于对前端传进来的参数进行校验，省去了大量的逻辑判断操作。
     * </p>
     */
    @ResponseBody
    @PostMapping("/login")
    public Map<String, Object> login(String imageCode, @Valid User user,
                                     BindingResult bindingResult, HttpSession session) {

        Map<String, Object> map = new HashMap<>();
        if (StringUtil.isEmpty(imageCode)) {
            map.put("success", false);
            map.put("errorInfo", "请输入验证码");
            return map;
        }
        if (!session.getAttribute(ConstantParam.CHECK_CODE).equals(imageCode)) {
            map.put("success", false);
            map.put("errorInfo", "验证码错误");
            return map;
        }
        if (bindingResult.hasErrors()) {
            map.put("success", false);
            map.put("errorInfo", bindingResult.getFieldError().getDefaultMessage());
            return map;
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());

        try {
            subject.login(token);
            String userName = (String) SecurityUtils.getSubject().getPrincipal();
            User currentUser = userService.findByUserName(userName);
            session.setAttribute("currentUser", currentUser);
            List<Role> roleList = roleService.findByUserId(currentUser.getId());
            map.put("roleList", roleList);
            map.put("roleSize", roleList.size());
            map.put("success", true);
            logService.save(new Log(Log.LOGIN_ACTION, "用户登录"));
            return map;
        } catch (Exception e) {
            logger.error("login errorInfo:" + e);
            map.put("success", false);
            map.put("errorInfo", "用户名或密码错误");
            return map;
        }
    }

    @ResponseBody
    @PostMapping("/saveRole")
    public Map<String, Object> saveRole(Integer roleId, HttpSession httpSession) throws Exception {
        Role currentRole = roleService.findById(roleId);
        httpSession.setAttribute("currentRole", currentRole);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

    @ResponseBody
    @RequestMapping("/loadUserInfo")
    public String loadUserInfo(HttpSession httpSession) throws Exception {
        User currentUser = (User) httpSession.getAttribute("currentUser");
        Role currentRole = (Role) httpSession.getAttribute("currentRole");
        return "欢迎您：" + currentUser.getTrueName() + "&nbsp;[&nbsp;" + currentRole.getName() + "&nbsp;]";
    }

    /**
     * [{"id":1,"text":"系统菜单","state":"closed","iconCls":"menu-plugin","attributes":{"url":null},
     * 	"children":[{"id":10,"text":"进货管理","state":"closed","iconCls":"menu-1","attributes":{"url":null},
     * 	"children":[{"id":1010,"text":"进货入库","state":"open","iconCls":"menu-11","attributes":{"url":"/purchase/purchase.html"}},
     * {"id":1020,"text":"退货出库","state":"open","iconCls":"menu-12","attributes":{"url":"/purchase/return.html"}},
     * {"id":1030,"text":"进货单据查询","state":"open","iconCls":"menu-13","attributes":{"url":"/purchase/purchaseSearch.html"}},
     * {"id":1040,"text":"退货单据查询","state":"open","iconCls":"menu-14","attributes":{"url":"/purchase/returnSearch.html"}},
     * {"id":1050,"text":"当前库存查询","state":"open","iconCls":"menu-15","attributes":{"url":"/common/stockSearch.html"}}]},
     * {"id":20,"text":"销售管理","state":"closed","iconCls":"menu-2","attributes":{"url":null},
     * 	"children":[{"id":2010,"text":"销售出库","state":"open","iconCls":"menu-21","attributes":{"url":"/sale/saleout.html"}},
     * {"id":2020,"text":"客户退货","state":"open","iconCls":"menu-22","attributes":{"url":"/sale/salereturn.html"}},
     * {"id":2030,"text":"销售单据查询","state":"open","iconCls":"menu-23","attributes":{"url":"/sale/saleSearch.html"}},
     * {"id":2040,"text":"客户退货查询","state":"open","iconCls":"menu-24","attributes":{"url":"/sale/returnSearch.html"}},
     * {"id":2050,"text":"当前库存查询","state":"open","iconCls":"menu-25","attributes":{"url":"/common/stockSearch.html"}}]},
     * {"id":30,"text":"库存管理","state":"closed","iconCls":"menu-3","attributes":{"url":null},
     * 	"children":[{"id":3010,"text":"商品报损","state":"open","iconCls":"menu-31","attributes":{"url":"/stock/damage.html"}},
     * {"id":3020,"text":"商品报溢","state":"open","iconCls":"menu-32","attributes":{"url":"/stock/overflow.html"}},
     * {"id":3030,"text":"库存报警","state":"open","iconCls":"menu-33","attributes":{"url":"/stock/alarm.html"}},
     * {"id":3040,"text":"报损报溢查询","state":"open","iconCls":"menu-34","attributes":{"url":"/stock/damageOverflowSearch.html"}},
     * {"id":3050,"text":"当前库存查询","state":"open","iconCls":"menu-35","attributes":{"url":"/common/stockSearch.html"}}]},
     * {"id":40,"text":"统计报表","state":"closed","iconCls":"menu-4","attributes":{"url":null},
     * 	"children":[{"id":4010,"text":"供应商统计","state":"open","iconCls":"menu-41","attributes":{"url":"/count/supplier.html"}},
     * {"id":4020,"text":"客户统计","state":"open","iconCls":"menu-42","attributes":{"url":"/count/customer.html"}},
     * {"id":4030,"text":"商品采购统计","state":"open","iconCls":"menu-43","attributes":{"url":"/count/purchase.html"}},
     * {"id":4040,"text":"商品销售统计","state":"open","iconCls":"menu-44","attributes":{"url":"/count/sale.html"}},
     * {"id":4050,"text":"按日统计分析","state":"open","iconCls":"menu-45","attributes":{"url":"/count/saleDay.html"}},
     * {"id":4060,"text":"按月统计分析","state":"open","iconCls":"menu-46","attributes":{"url":"/count/saleMonth.html"}}]},
     * {"id":50,"text":"基础资料","state":"closed","iconCls":"menu-5","attributes":{"url":null},
     * 	"children":[{"id":5010,"text":"供应商管理","state":"open","iconCls":"menu-51","attributes":{"url":"/basicData/supplier.html"}},
     * {"id":5020,"text":"客户管理","state":"open","iconCls":"menu-52","attributes":{"url":"/basicData/customer.html"}},
     * {"id":5030,"text":"商品管理","state":"open","iconCls":"menu-53","attributes":{"url":"/basicData/goods.html"}},
     * {"id":5040,"text":"期初库存","state":"open","iconCls":"menu-54","attributes":{"url":"/basicData/stock.html"}}]},
     * {"id":60,"text":"系统管理","state":"closed","iconCls":"menu-6","attributes":{"url":null},
     * 	"children":[{"id":6010,"text":"角色管理","state":"open","iconCls":"menu-61","attributes":{"url":"/power/role.html"}},
     * {"id":6020,"text":"用户管理","state":"open","iconCls":"menu-62","attributes":{"url":"/power/user.html"}},
     * {"id":6030,"text":"系统日志","state":"open","iconCls":"menu-65","attributes":{"url":"/power/log.html"}},
     * {"id":6040,"text":"修改密码","state":"open","iconCls":"menu-63","attributes":{"url":null}},
     * {"id":6050,"text":"安全退出","state":"open","iconCls":"menu-64","attributes":{"url":null}}]}]}]
     * @param httpSession
     * @param parentId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/loadMenuInfo")
    public String loadMenuInfo(HttpSession httpSession, Integer parentId) throws Exception {
        Role currentRole = (Role) httpSession.getAttribute("currentRole");
        return getAllMenuByParentId(parentId, currentRole.getId()).toString();
    }

    /**
     * 获取所有菜单 信息
     *
     * @param parentId
     * @param roleId
     * @return
     */
    private JsonArray getAllMenuByParentId(Integer parentId, Integer roleId) {
        JsonArray jsonArray = this.getMenuByParentId(parentId, roleId);
        for(int i=0;i<jsonArray.size();i++){
            JsonObject jsonObject=(JsonObject) jsonArray.get(i);
            if("open".equals(jsonObject.get("state").getAsString())){
                continue;
            }else{
                jsonObject.add("children", getAllMenuByParentId(jsonObject.get("id").getAsInt(),roleId));
            }
        }
        return jsonArray;
    }

    private JsonArray getMenuByParentId(Integer parentId, Integer roleId) {
        List<Menu> menuList = menuService.findByParentIdAndRoleId(parentId, roleId);
        JsonArray jsonArray = new JsonArray();
        menuList.forEach(menu -> {
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("id", menu.getId()); // 节点id
            jsonObject.addProperty("text", menu.getName()); // 节点名称
            if(menu.getState()==1){
                jsonObject.addProperty("state", "closed"); // 根节点
            }else{
                jsonObject.addProperty("state", "open"); // 叶子节点
            }
            jsonObject.addProperty("iconCls", menu.getIcon());
            JsonObject attributeObject=new JsonObject(); // 扩展属性
            attributeObject.addProperty("url", menu.getUrl()); // 菜单请求地址
            jsonObject.add("attributes", attributeObject);
            jsonArray.add(jsonObject);
        });
        return jsonArray;
    }
}
