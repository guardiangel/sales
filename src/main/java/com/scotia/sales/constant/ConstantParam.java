package com.scotia.sales.constant;

/**
 * 常量参数类
 */
public final class ConstantParam {

    //商品类别根节点的父节点
    public final static int GOODSTYPE_ROOT_PARENT_ID = -1;
    //商品类别根节点
    public final static int GOODSTYPE_ROOT_ID = 1;

    //商品类别 代表叶节点，没有叶子节点
    public final static String GOODSTYPE_NODE_OPEN = "open";
    //商品类别 代表根节点，可以展示查询叶子节点
    public final static String GOODSTYPE_NODE_CLOSE = "close";

    //商品类别 代表叶节点，没有叶子节点
    public final static int GOODSTYPE_STATE_LEAF = 0;
    //商品类别 代表根节点，可以展示查询叶子节点
    public final static int GOODSTYPE_STATE_NOT_LEAF = 1;

    //图标，非根节点的情况
    public final static String GOODSTYPE_ICON_FOLDER = "icon-folder";

    //进货单前缀
    public static final String PURCHASE_LIST_PREFIX = "JH";
    //默认的进货单编号
    public static final String PURCHASE_LIST_CODE_DEFAULT = "0001";

    //退货单前缀
    public static final String RETURN_LIST_PREFIX = "TH";
    //默认的退货单编号
    public static final String RETURN_LIST_CODE_DEFAULT = "0001";

    //商品初始状态
    public static final int GOODS_STATE_INITIATE = 0;

    //商品期初库存入仓库
    public static final int GOODS_STATE_STORE = 1;

    //有进货或者销售单据
    public static final int GOODS_STATE_INOROUT = 2;


}
