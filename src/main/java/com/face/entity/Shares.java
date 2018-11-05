package com.face.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * @author huangwh
 * @date 2018/10/27
 * @time 15:01
 */
@Entity
@Table(name = "shares")
public class Shares {

    @Id
    private String id;

    @Column(name = "status")
    private String status;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinColumn(name = "shares_id")
    private List<Share> hq;

    @Column(name = "code")
    private String code;

    @Column(name = "code_name")
    private String codeName;

    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinColumn(name = "share_stat_id")
    private ShareStat stat;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Share> getHq() {
        return hq;
    }

    public void setHq(List<Share> hq) {
        this.hq = hq;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ShareStat getStat() {
        return stat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStat(ShareStat stat) {
        this.stat = stat;
    }

    public Shares() {
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
