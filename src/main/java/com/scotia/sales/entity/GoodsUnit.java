package com.scotia.sales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Felix
 */
@Entity
@Table(name = "t_goodsunit")
public class GoodsUnit {

    /**
     * 商品单位 ID
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 商品单位名称
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoodsUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
