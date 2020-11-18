package com.scotia.sales.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * 日志实体
 */
@Entity
@Table(name = "t_log")
public class Log {

    public final static String LOGIN_ACTION = "登录操作";
    public final static String LOGOUT_ACTION = "注销操作";
    public final static String SEARCH_ACTION = "查询操作";
    public final static String UPDATE_ACTION = "更新操作";
    public final static String ADD_ACTION = "添加操作";
    public final static String DELETE_ACTION = "删除操作";

    @Id
    @GeneratedValue
    private Integer id;// 编号

    @Column(length = 100)
    private String type;//日志类型

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;//操作用户，使用userId关联，表示当前日志表有一个字段叫userId

    @Column(length = 1000)
    private String content;//操作内容

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;//操作时间

    @Transient
    private Date btime;//起始时间

    @Transient
    private Date etime;//结束时间

    public Log() {
    }

    public Log(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //@JsonIgnore 该注解的作用是转成json时不返回给前端
    //JsonSerialize 作用：用于在序列化是加入开发者的代码(这句话网上抄的)，常用于对象或属性上，前提是对象实现了Serializable接口
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getBtime() {
        return btime;
    }

    public void setBtime(Date btime) {
        this.btime = btime;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }
}
