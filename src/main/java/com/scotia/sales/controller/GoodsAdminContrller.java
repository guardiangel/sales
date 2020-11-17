package com.scotia.sales.controller;

import com.scotia.sales.entity.Goods;
import com.scotia.sales.entity.Log;
import com.scotia.sales.service.GoodsService;
import com.scotia.sales.service.LogService;
import com.scotia.sales.service.SaleListGoodsService;
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
    private LogService logService;

    /**
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> listInventory(Goods goods, @RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "rows", required = false) Integer rows) {

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


}
