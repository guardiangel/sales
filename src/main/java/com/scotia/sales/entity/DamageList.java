package com.scotia.sales.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Felix
 */
@Entity
@Table(name = "t_damage_list")
public class DamageList {
    @Id
    @GeneratedValue
    /** 编号 */
    private Integer id;

    @Column(length = 100)
    /** 报损单号 */
    private String damageNumber;


    @Temporal(TemporalType.TIMESTAMP)
    /** 报损日期 */
    private Date damageDate;
    /**
     * 起始时间 搜索用到
     */
    @Transient
    private Date bDamageDate;

    @Transient
    /** 结束时间 搜索用到 */
    private Date eDamageDate;


    @ManyToOne
    @JoinColumn(name = "user_id")
    /** 操作用户 */
    private User user;

    @Column(length = 1000)
    /** 备注 */
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDamageNumber() {
        return damageNumber;
    }

    public void setDamageNumber(String damageNumber) {
        this.damageNumber = damageNumber;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getDamageDate() {
        return damageDate;
    }

    public void setDamageDate(Date damageDate) {
        this.damageDate = damageDate;
    }

    public Date getbDamageDate() {
        return bDamageDate;
    }

    public void setbDamageDate(Date bDamageDate) {
        this.bDamageDate = bDamageDate;
    }

    public Date geteDamageDate() {
        return eDamageDate;
    }

    public void seteDamageDate(Date eDamageDate) {
        this.eDamageDate = eDamageDate;
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
        return "DamageList{" +
                "id=" + id +
                ", damageNumber='" + damageNumber + '\'' +
                ", damageDate=" + damageDate +
                ", bDamageDate=" + bDamageDate +
                ", eDamageDate=" + eDamageDate +
                ", user=" + user +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
