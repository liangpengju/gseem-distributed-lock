package com.gseem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author feiyue
 * @date 2019/11/9
 */
@Entity
@Table(name = "user")
@Data
public class User extends BaseEntity{

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "age")
    private int age;

}
