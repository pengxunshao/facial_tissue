package com.dida.facialtissue.entity;

import java.io.Serializable;
import java.util.Date;

public class Subject implements Serializable{
	private static final long serialVersionUID = -6443451135722333601L;

	//主键ID
	private Long id;

	//创建时间
    private Date gmtCreate;

    //更新时间
    private Date gmtModify;

    //学科名称
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}