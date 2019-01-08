package com.face.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author huangwh
 * @date 2019/1/7
 * @time 17:35
 */
@Entity
@Table(name = "config")
public class Config {

    @Id
    private String id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "VALUE",columnDefinition ="TEXT" )
    private String value;

    @Column(name = "DESC",columnDefinition ="TEXT")
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
