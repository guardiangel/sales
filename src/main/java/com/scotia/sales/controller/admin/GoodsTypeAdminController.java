package com.scotia.sales.controller.admin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scotia.sales.constant.ConstantParam;
import com.scotia.sales.entity.GoodsType;
import com.scotia.sales.entity.Log;
import com.scotia.sales.service.GoodsTypeService;
import com.scotia.sales.service.LogService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品类别管理类
 */
@RestController
@RequestMapping("/admin/goodsType")
public class GoodsTypeAdminController {

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private LogService logService;


    @RequestMapping("/save")
    @RequiresPermissions(value = {"商品管理", "进货入库"}, logical = Logical.OR)
    public Map<String, Object> save(String name, Integer parentId) {

        Map<String, Object> resultMap = new HashMap<>();
        GoodsType goodsType = new GoodsType();
        goodsType.setName(name);
        goodsType.setpId(parentId);
        goodsType.setIcon(ConstantParam.GOODSTYPE_ICON_FOLDER);
        goodsType.setState(ConstantParam.GOODSTYPE_STATE_LEAF);//当前节点为叶子节点
        logService.save(new Log(Log.ADD_ACTION,"添加商品类别信息"+goodsType));
        goodsTypeService.save(goodsType);

        //把父节点的state设置为1，无论原来父节点的state是否为1，均重新设置一次
        GoodsType parentGoodsType = goodsTypeService.findById(parentId);
        parentGoodsType.setState(ConstantParam.GOODSTYPE_STATE_NOT_LEAF);
        goodsTypeService.save(parentGoodsType);
        resultMap.put("success", true);

        return resultMap;
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"商品管理", "进货入库"}, logical = Logical.OR)
    public Map<String, Object> delete(Integer id) {

        Map<String, Object> resultMap = new HashMap<>();
        GoodsType goodsType = goodsTypeService.findById(id);

        //如果当前节点的父节点下，只有一个节点，也就是当前节点，则把父节点的state调整为0，表示叶子节点
        if (goodsTypeService.findByParentId(goodsType.getId()).size() == 1) {
            GoodsType parentGoodsType = goodsTypeService.findById(goodsType.getpId());
            parentGoodsType.setState(ConstantParam.GOODSTYPE_STATE_LEAF);
            goodsTypeService.save(parentGoodsType);
        }

        logService.save(new Log(Log.DELETE_ACTION, "删除商品类别信息" + goodsType));
        goodsTypeService.delete(id);//删除当前节点
        resultMap.put("success", true);

        return resultMap;
    }


    /**
     * [
     * {
     * "id":1,"text":"所有类别","state":"close","iconCls":"icon-folderOpen","attributes":{"state":1},"children":
     * [
     * {"id":2,"text":"服饰","state":"close","iconCls":"icon-folder","attributes":{"state":1},"children":
     * [
     * {"id":6,"text":"连衣裙","state":"open","iconCls":"icon-folder","attributes":{"state":0}},
     * {"id":7,"text":"男士西装","state":"open","iconCls":"icon-folder","attributes":{"state":0}},
     * {"id":8,"text":"牛仔裤","state":"open","iconCls":"icon-folder","attributes":{"state":0}}]},
     * {"id":3,"text":"食品","state":"close","iconCls":"icon-folder","attributes":{"state":1},"children":
     * [
     * {"id":9,"text":"进口食品","state":"open","iconCls":"icon-folder","attributes":{"state":0}},
     * {"id":10,"text":"地方特产","state":"open","iconCls":"icon-folder","attributes":{"state":0}},
     * {"id":11,"text":"休闲食品","state":"open","iconCls":"icon-folder","attributes":{"state":0}}]},
     * {"id":4,"text":"家电","state":"close","iconCls":"icon-folder","attributes":{"state":1},"children":
     * [
     * {"id":12,"text":"电视机","state":"open","iconCls":"icon-folder","attributes":{"state":0}},
     * {"id":13,"text":"洗衣机","state":"open","iconCls":"icon-folder","attributes":{"state":0}},
     * {"id":14,"text":"冰箱","state":"open","iconCls":"icon-folder","attributes":{"state":0}}]},
     * {"id":5,"text":"数码","state":"close","iconCls":"icon-folder","attributes":{"state":1},"children":
     * [
     * {"id":15,"text":"相机","state":"open","iconCls":"icon-folder","attributes":{"state":0}},
     * {"id":16,"text":"手机","state":"open","iconCls":"icon-folder","attributes":{"state":0}},
     * {"id":17,"text":"音箱","state":"open","iconCls":"icon-folder","attributes":{"state":0}}]}
     * ]
     * }
     * ]
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/loadTreeInfo")
    @RequiresPermissions(value = {"商品管理", "进货入库", "当前库存查询"}, logical = Logical.OR)
    public String loadTreeInfo() throws Exception {
        logService.save(new Log(Log.SEARCH_ACTION, "查询商品类别信息"));
        String result = getAllByParentId(ConstantParam.GOODSTYPE_ROOT_PARENT_ID).toString();
        return result;
    }

    /**
     * 根据商品类别根父节点，查询所有的商品类别信息
     *
     * @param goodstypeRootParentId
     * @return
     */
    private JsonArray getAllByParentId(int goodstypeRootParentId) {
        JsonArray jsonArray = this.getByParentId(goodstypeRootParentId);
        jsonArray.forEach(jsonElement -> {
            JsonObject jsonObject = (JsonObject) jsonElement;
            if (!ConstantParam.GOODSTYPE_NODE_OPEN
                    .equals(jsonObject.get("state").getAsString())) {
                jsonObject.add("children",
                        getAllByParentId(jsonObject.get("id").getAsInt()));
            }
        });
        return jsonArray;
    }

    /**
     * 根据父节点查询子节点
     *
     * @param parentId
     * @return
     */
    private JsonArray getByParentId(int parentId) {
        JsonArray jsonArray = new JsonArray();
        List<GoodsType> goodsTypeList = goodsTypeService.findByParentId(parentId);

        goodsTypeList.forEach(goodsType -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", goodsType.getId());
            jsonObject.addProperty("text", goodsType.getName());
            jsonObject.addProperty("state",
                    goodsType.getState() == ConstantParam.GOODSTYPE_STATE_NOT_LEAF ?
                            ConstantParam.GOODSTYPE_NODE_CLOSE
                            : ConstantParam.GOODSTYPE_NODE_OPEN);
            jsonObject.addProperty("iconCls", goodsType.getIcon());
            JsonObject attributes = new JsonObject();
            attributes.addProperty("state", goodsType.getState());
            jsonObject.add("attributes", attributes);
            jsonArray.add(jsonObject);
        });

        return jsonArray;
    }


}
