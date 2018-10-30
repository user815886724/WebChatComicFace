package com.face.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * @author huangwh
 * @date 2018/10/27
 * @time 20:06
 */
@Entity
@Table(name = "share_stat")
public class ShareStat {

    @Id
    private String id;

    @Column(name = "table_desc")
    private String tableDesc;

    @Column(name = "time_desc")
    private String timeDesc;

    //累计涨幅额
    @Column(name = "rise_fall_amount_count")
    private Double riseFallAmountCount;

    //累计涨跌幅
    @Column(name = "rise_fall_range_count")
    private String riseFallRangeCount;

    //最低价
    @Column(name = "lowest")
    private Double lowest;

    //最高价
    @Column(name = "highest")
    private Double highest;

    //累计成交量
    @Column(name = "volume_count")
    private Double volumeCount;

    //累计成交额
    @Column(name = "turnover_count")
    private Double turnoverCount;

    //累计换手率
    @Column(name = "turnover_rate_count")
    private String turnoverRateCount;

    public ShareStat() {
    }

    public ShareStat(String tableDesc, String timeDesc, Double riseFallAmountCount, String riseFallRangeCount, Double lowest, Double highest, Double volumeCount, Double turnoverCount, String turnoverRateCount) {
        this.id = UUID.randomUUID().toString();
        this.tableDesc = tableDesc;
        this.timeDesc = timeDesc;
        this.riseFallAmountCount = riseFallAmountCount;
        this.riseFallRangeCount = riseFallRangeCount;
        this.lowest = lowest;
        this.highest = highest;
        this.volumeCount = volumeCount;
        this.turnoverCount = turnoverCount;
        this.turnoverRateCount = turnoverRateCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRiseFallRangeCount() {
        return riseFallRangeCount;
    }

    public void setRiseFallRangeCount(String riseFallRangeCount) {
        this.riseFallRangeCount = riseFallRangeCount;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public Double getRiseFallAmountCount() {
        return riseFallAmountCount;
    }

    public void setRiseFallAmountCount(Double riseFallAmountCount) {
        this.riseFallAmountCount = riseFallAmountCount;
    }


    public Double getLowest() {
        return lowest;
    }

    public void setLowest(Double lowest) {
        this.lowest = lowest;
    }

    public Double getHighest() {
        return highest;
    }

    public void setHighest(Double highest) {
        this.highest = highest;
    }

    public Double getVolumeCount() {
        return volumeCount;
    }

    public void setVolumeCount(Double volumeCount) {
        this.volumeCount = volumeCount;
    }

    public Double getTurnoverCount() {
        return turnoverCount;
    }

    public void setTurnoverCount(Double turnoverCount) {
        this.turnoverCount = turnoverCount;
    }

    public String getTurnoverRateCount() {
        return turnoverRateCount;
    }

    public void setTurnoverRateCount(String turnoverRateCount) {
        this.turnoverRateCount = turnoverRateCount;
    }
}
