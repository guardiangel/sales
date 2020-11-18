package com.scotia.sales.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_sale_list")
public class SaleList {

    @Id
    @GeneratedValue
    private Integer id; // 编号

    @Column(length=100)
    private String saleNumber; // 销售单号

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date saleDate; // 销售日期

    @Transient
    private Date bSaleDate; // 起始时间 搜索用到

    @Transient
    private Date eSaleDate; // 结束时间 搜索用到

    private float amountPayable; // 应付金额

    private float amountPaid; // 实付金额

    private Integer state; // 交易状态 1 已付  2 未付

    @Transient
    private List<SaleListGoods> saleListGoodsList;// 销售单商品集合

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length=1000)
    private String remarks; // 备注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(String saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Date getbSaleDate() {
        return bSaleDate;
    }

    public void setbSaleDate(Date bSaleDate) {
        this.bSaleDate = bSaleDate;
    }

    public Date geteSaleDate() {
        return eSaleDate;
    }

    public void seteSaleDate(Date eSaleDate) {
        this.eSaleDate = eSaleDate;
    }

    public float getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(float amountPayable) {
        this.amountPayable = amountPayable;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<SaleListGoods> getSaleListGoodsList() {
        return saleListGoodsList;
    }

    public void setSaleListGoodsList(List<SaleListGoods> saleListGoodsList) {
        this.saleListGoodsList = saleListGoodsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "SaleList [id=" + id + ", saleNumber=" + saleNumber + ", customer=" + customer + ", saleDate=" + saleDate
                + ", bSaleDate=" + bSaleDate + ", eSaleDate=" + eSaleDate + ", amountPayable=" + amountPayable
                + ", amountPaid=" + amountPaid + ", state=" + state + ", user=" + user + ", remarks=" + remarks + "]";
    }
}
