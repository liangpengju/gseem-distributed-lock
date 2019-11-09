package com.gseem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    protected String id;

    /**
     * 创建时间
     */
    @Column(name= "create_date")
    Date createDate = new Date();

    /**
     * 更新时间
     */
    @Column(name= "update_date")
    Date updateDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
