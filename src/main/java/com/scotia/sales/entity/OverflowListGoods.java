package com.scotia.sales.entity;

import javax.persistence.*;

/***
 * @author Felix
 */
@Entity
@Table(name = "t_overflow_list_goods")
public class OverflowListGoods {
    @Id
    @GeneratedValue
    private Integer id; // 编号

    @ManyToOne
    @JoinColumn(name="overflow_list_id")
    private OverflowList overflowList; // 报溢单

    @Column(length=50)
    private String code; // 商品编码


    @Column(length=50)
    private String name; // 商品名称

    @Column(length=50)
    private String model; // 商品型号

    @ManyToOne
    @JoinColumn(name="type_id")
    private GoodsType type; // 商品类别

    @Transient
    private Integer typeId; // 类别id

    private Integer goodsId; // 商品id

    @Column(length=10)
    private String unit; // 商品单位

    private float price; // 单价

    private int num; // 数量

    private float total; // 总价

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OverflowList getOverflowList() {
        return overflowList;
    }

    public void setOverflowList(OverflowList overflowList) {
        this.overflowList = overflowList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OverflowListGoods [id=" + id + ", code=" + code + ", name=" + name + ", model=" + model + ", type="
                + type + ", unit=" + unit + ", price=" + price + ", num=" + num + ", total=" + total + "]";
    }
}
