package com.scotia.sales.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scotia.sales.entity.CustomDateTimeSerializer;
import com.scotia.sales.entity.Supplier;
import com.scotia.sales.entity.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_return_list")
public class ReturnList {

    @Id
    @GeneratedValue
    private Integer id;//编号

    @Column(length = 100)
    public String returnNumber;// 退货单号

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;// 供应商

    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;//退货日期

    private float amountPayable;// 应付金额

    private float amountPaid;// 实付金额

    private Integer state; // 交易状态 1 已付  2 未付

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 操作用户

    @Column(length=1000)
    private String remarks; // 备注

    @Transient
    private Date bReturnDate; // 起始时间 搜索用到

    @Transient
    private Date eReturnDate; // 结束时间 搜索用到

    @Transient
    private List<ReturnListGoods> returnListGoodsList=null; // 退货单商品集合

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
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

    public Date getbReturnDate() {
        return bReturnDate;
    }

    public void setbReturnDate(Date bReturnDate) {
        this.bReturnDate = bReturnDate;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date geteReturnDate() {
        return eReturnDate;
    }

    public void seteReturnDate(Date eReturnDate) {
        this.eReturnDate = eReturnDate;
    }

    public List<ReturnListGoods> getReturnListGoodsList() {
        return returnListGoodsList;
    }

    public void setReturnListGoodsList(List<ReturnListGoods> returnListGoodsList) {
        this.returnListGoodsList = returnListGoodsList;
    }

    @Override
    public String toString() {
        return "ReturnList [id=" + id + ", returnNumber=" + returnNumber + ", supplier=" + supplier + ", returnDate="
                + returnDate + ", amountPayable=" + amountPayable + ", amountPaid=" + amountPaid + ", state=" + state
                + ", user=" + user + ", remarks=" + remarks + ", bReturnDate=" + bReturnDate + ", eReturnDate="
                + eReturnDate + "]";
    }
}
