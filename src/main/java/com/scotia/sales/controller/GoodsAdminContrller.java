package com.scotia.sales.controller;

import com.scotia.sales.entity.Goods;
import com.scotia.sales.entity.Log;
import com.scotia.sales.service.CustomerReturnListGoodsService;
import com.scotia.sales.service.GoodsService;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.SaleListGoodsService;
import com.scotia.sales.util.StringUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理商品Controller
 * <p>
 *
 * @RestController相当于@Controller+@ResponseBody两个注解的结合，返回json数据不需要在方法前面加@ResponseBody注解了， 但使用@RestController这个注解，就不能返回jsp,html页面，视图解析器无法解析jsp,html页面
 * </p>
 * <p>
 * @RequestParam：将请求参数绑定到你控制器的方法参数上（是springmvc中接收普通参数的注解） 语法：@RequestParam(value=”参数名”,required=”true/false”,defaultValue=””)
 * value：参数名
 * required：是否包含该参数，默认为true，表示该请求路径中必须包含该参数，如果不包含就报错。
 * <p>
 * defaultValue：默认参数值，如果设置了该值，required=true将失效，自动为false,如果没有传该参数，就使用默认值
 *
 * </p>
 */
@RestController
@RequestMapping("/admin/goods/")
public class GoodsAdminContrller {
    @Resource
    private GoodsService goodsService;

    @Resource
    private SaleListGoodsService saleListGoodsService;

    @Resource
    private CustomerReturnListGoodsService customerReturnListGoodsService;

    @Resource
    private LogService logService;

    /**
     * 根据分页条件查询商品库存信息
     * <p>
     *
     * @param goods
     * @param page
     * @param rows
     * @return
     * @throws Exception
     * @RequiresPermissions shiro中使用的权限验证机制，
     * 需要实现AuthorizingRealm的doGetAuthorizationInfo方法。
     * 本项目中的用法，需要把菜单的名称放入到SimpleAuthorizationInfo中。
     * </p>
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = {"商品管理", "进货入库"}, logical = Logical.OR)
    public Map<String, Object> list(Goods goods,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "rows", required = false) Integer rows) throws Exception {

        Map<String, Object> result = new HashMap<>();
        List<Goods> goodsList = goodsService.list(goods, page, rows, Sort.Direction.ASC, "id");
        Long total = goodsService.count(goods);
        result.put("rows", goodsList);
        result.put("total", total);
        logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息"));
        return result;
    }

    /**
     * 根据条件分页查询商品库存信息
     *
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/listInventory")
    @RequiresPermissions(value = {"当前库存查询"})
    public Map<String, Object> listInventory(Goods goods,
                                             @RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "rows", required = false) Integer rows) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<Goods> goodsList = goodsService.list(goods, page, rows, Sort.Direction.ASC, "id");
        Long count = goodsService.count(goods);
        goodsList.forEach(goods1 -> {
            goods1.setSaleTotal(saleListGoodsService.getTotalByGoodsId(goods1.getId())
                    - customerReturnListGoodsService.getTotalByGoodsId(goods1.getId()));//设置销售总数
        });

        result.put("rows", goodsList);
        result.put("total", count);
        logService.save(new Log(Log.SEARCH_ACTION, "查询商品库存信息")); // 写入日志
        return result;
    }

    /**
     * 查询库存报警商品信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/listAlarm")
    @RequiresPermissions(value = {"库存报警"})
    public Map<String, Object> listAlarm() throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put("rows", goodsService.listAlarm());
        return result;
    }

    /**
     * 根据条件分页查询没有库存的商品信息
     *
     * @param codeOrName
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/listNoInventoryQuantity")
    @RequiresPermissions(value = {"期初库存"})
    public Map<String, Object> listNoInventoryQuantity(@RequestParam(value = "codeOrName", required = false) String codeOrName,
                                                       @RequestParam(value = "page", required = false) Integer page,
                                                       @RequestParam(value = "rows", required = false) Integer rows) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<Goods> goodsList = goodsService.listNoInventoryQuantityByCodeOrName(codeOrName, page, rows, Sort.Direction.ASC, "id");
        Long total = goodsService.getCountNoInventoryQuantityByCodeOrName(codeOrName);
        resultMap.put("rows", goodsList);
        resultMap.put("total", total);
        logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息（无库存）")); // 写入日志
        return resultMap;
    }

    /**
     * 查询有库存的商品信息
     *
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/listHasInventoryQuantity")
    @RequiresPermissions(value = {"期初库存"})
    public Map<String, Object> listHasInventoryQuantity(@RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "rows", required = false) Integer rows) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<Goods> goodsList = goodsService.listHasInventoryQuantity(page, rows, Sort.Direction.ASC, "id");
        Long total = goodsService.getCountHasInventoryQuantity();
        resultMap.put("rows", goodsList);
        resultMap.put("total", total);
        logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息（有库存）")); // 写入日志
        return resultMap;
    }

    /**
     * 删除库存信息，把商品库存设置为0
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteStock")
    @RequiresPermissions(value = {"期初库存"})
    public Map<String, Object> deleteStock(Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        Goods goods = goodsService.findById(id);

        if (goods.getState() == 2) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "该商品已经发生单据，不能删除！");
        } else {
            goods.setInventoryQuantity(0);
            goodsService.save(goods);
            resultMap.put("success", true);
        }

        return resultMap;
    }

    /**
     * 生成商品编码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/genGoodsCode")
    @RequiresPermissions(value = {"商品管理"})
    public String genGoodsCode() throws Exception {
        String maxGoodsCode = goodsService.getMaxGoodsCode();
        if (StringUtil.isNotEmpty(maxGoodsCode)) {
            Integer code = Integer.valueOf(maxGoodsCode) + 1;
            String codes = code.toString();
            int length = codes.length();
            for (int i = 4; i > length; i--) {
                codes = "0" + codes;
            }
            return codes;
        } else {
            return "0001";
        }
    }

    @RequestMapping("/save")
    @RequiresPermissions(value = {"商品管理", "进货入库"}, logical = Logical.OR)
    public Map<String, Object> save(Goods goods) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        logService.save(goods.getId() == null ? new Log(Log.ADD_ACTION, "添加商品信息" + goods)
                : new Log(Log.UPDATE_ACTION, "更新商品信息" + goods));

        if (goods.getId() != null) {
            goods.setLastPurchasingPrice(goods.getPurchasingPrice());//设置上次价格为当前价格
        }
        goodsService.save(goods);
        resultMap.put("success", true);
        return resultMap;

    }

    @RequestMapping("/saveStore")
    @RequiresPermissions(value = {"期初库存"})
    public Map<String, Object> saveStore(Integer id, Integer num, Float price) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        Goods goods = goodsService.findById(id);
        goods.setInventoryQuantity(num);
        goods.setPurchasingPrice(price);
        goodsService.save(goods);
        logService.save(new Log(Log.UPDATE_ACTION, "修改商品" + goods + "，价格=" + price + ",库存=" + num)); // 写入日志
        resultMap.put("success", true);
        return resultMap;

    }

    /**
     * 删除商品信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    @RequiresPermissions(value = { "商品管理" })
    public Map<String,Object> delete(Integer id)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();

        Goods goods = goodsService.findById(id);
        switch (goods.getState()) {
            case 1:
                resultMap.put("success", false);
                resultMap.put("errorInfo", "该商品已经期初入库，不能删除！");
                break;
            case 2:
                resultMap.put("success", false);
                resultMap.put("errorInfo", "该商品已经发生单据，不能删除！");
                break;
            default:
                logService.save(new Log(Log.DELETE_ACTION,"删除商品信息"+goodsService.findById(id)));  // 写入日志
                goodsService.delete(id);
                resultMap.put("success", true);
                break;
        }
        return resultMap;
    }


}
