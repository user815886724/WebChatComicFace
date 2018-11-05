package com.face.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * @author huangwh
 * @date 2018/10/27
 * @time 15:04
 */
@Entity
@Table(name = "share")
public class Share {

    @Id
    private String id;

    @Column(name = "time")
    private String time;

    //开盘价
    @Column(name = "open_price")
    private Double openPrice;

    //收盘价
    @Column(name = "close_price")
    private Double closePrice;

    //涨幅额
    @Column(name = "rise_fall_amount")
    private Double riseFallAmount;

    //涨跌幅
    @Column(name = "rise_fall_range")
    private String riseFallRange;

    //最低价
    @Column(name = "lowest")
    private Double lowest;

    //最高价
    @Column(name = "highest")
    private Double highest;

    //成交量
    @Column(name = "volume")
    private Double volume;

    //成交额
    @Column(name = "turnover")
    private Double turnover;

    //换手率
    @Column(name = "turnover_rate")
    private String turnoverRate;

    //股票代码
    @Column(name = "share_code")
    private String shareCode;

    @Column(name = "shares_id")
    private String sharesId;

    public Share(String time, Double openPrice, Double closePrice, Double riseFallAmount, String riseFallRange, Double lowest, Double highest, Double volume, Double turnover, String turnoverRate,String shareCode) {
        this.id = UUID.randomUUID().toString();
        this.time = time;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.riseFallAmount = riseFallAmount;
        this.riseFallRange = riseFallRange;
        this.lowest = lowest;
        this.highest = highest;
        this.volume = volume;
        this.turnover = turnover;
        this.turnoverRate = turnoverRate;
        this.shareCode = shareCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(String turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public Share() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getRiseFallAmount() {
        return riseFallAmount;
    }

    public void setRiseFallAmount(Double riseFallAmount) {
        this.riseFallAmount = riseFallAmount;
    }

    public String getRiseFallRange() {
        return riseFallRange;
    }

    public void setRiseFallRange(String riseFallRange) {
        this.riseFallRange = riseFallRange;
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

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getSharesId() {
        return sharesId;
    }

    public void setSharesId(String sharesId) {
        this.sharesId = sharesId;
    }
}
