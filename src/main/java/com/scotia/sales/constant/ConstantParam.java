package com.scotia.sales.constant;

/**
 * @author Felix
 * 常量参数类
 */
public final class ConstantParam {

    public static final String CHECK_CODE = "checkcode";

    /**
     * 商品类别根节点的父节点
     */
    public final static int GOODSTYPE_ROOT_PARENT_ID = -1;
    /**
     * 商品类别根节点
     */
    public final static int GOODSTYPE_ROOT_ID = 1;

    /**
     * 商品类别 代表叶节点，没有叶子节点
     */
    public final static String GOODSTYPE_NODE_OPEN = "open";
    /**
     * 商品类别 代表根节点，可以展示查询叶子节点
     */
    public final static String GOODSTYPE_NODE_CLOSE = "close";

    /**
     * 商品类别 代表叶节点，没有叶子节点
     */
    public final static int GOODSTYPE_STATE_LEAF = 0;
    /**
     * 商品类别 代表根节点，可以展示查询叶子节点
     */
    public final static int GOODSTYPE_STATE_NOT_LEAF = 1;

    /**
     * 图标，非根节点的情况
     */
    public final static String GOODSTYPE_ICON_FOLDER = "icon-folder";

    /**
     * 进货单前缀
     */
    public static final String PURCHASE_LIST_PREFIX = "JH";
    /**
     * 默认的进货单编号
     */
    public static final String PURCHASE_LIST_CODE_DEFAULT = "0001";

    /**
     * 退货单前缀
     */
    public static final String RETURN_LIST_PREFIX = "TH";
    /**
     * 默认的退货单编号
     */
    public static final String RETURN_LIST_CODE_DEFAULT = "0001";

    /**
     * 商品初始状态
     */
    public static final int GOODS_STATE_INITIATE = 0;

    /**
     * 商品期初库存入仓库
     */
    public static final int GOODS_STATE_STORE = 1;

    /**
     * 有进货或者销售单据
     */
    public static final int GOODS_STATE_INOROUT = 2;

    /**
     * 销售单号前缀
     */
    public static final String SALE_LIST_PREFIX = "TH";

    /**
     * 默认的销售单编号
     */
    public static final String SALE_LIST_CODE_DEFAULT = "0001";

    /**
     * 客户退货单号前缀
     */
    public static final String CUSTOMER_RETURN_LIST_PREFIX = "XH";

    /**
     * 默认的客户退货单编号
     */
    public static final String CUSTOMER_RETURN_LIST_CODE_DEFAULT = "0001";

    /**
     * 商品报损单前缀
     */
    public static final String DAMAGE_LIST_PREFIX = "BS";
    /**
     * 商品报损单编号
     */
    public static final String DAMAGE_LIST_CODE_DEFAULT = "0001";
    /**
     * 商品报溢单前缀
     */
    public static final String OVERFLOW_LIST_PREFIX = "BY";
    /**
     * 商品报溢单编号
     */
    public static final String OVERFLOW_LIST_CODE_DEFAULT = "0001";

    /**
     * 购买的产品支付状态，1是已支付，2是未支付
     */
    public static final int PURCHASELIST_STATE_PAID = 1;

    public static final int PURCHASELIST_STATE_NOT_PAID = 2;

    /**
     * 退货产品支付状态，1是已支付，2是未支付
     */
    public static final int RETURNLIST_STATE_PAID = 1;

    public static final int RETURNLIST_STATE_NOT_PAID = 2;

    /**
     * 卖出产品支付状态，1是已支付，2是未支付
     */
    public static final int SALELIST_STATE_PAID = 1;

    public static final int SALELIST_STATE_NOT_PAID = 2;

 /**
     * 卖出产品支付状态，1是已支付，2是未支付
     */
    public static final int CUSTOMERRETURNLIST_STATE_PAID = 1;

    public static final int CUSTOMERRETURNLIST_STATE_NOT_PAID = 2;


}
