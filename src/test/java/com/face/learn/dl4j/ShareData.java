package com.face.learn.dl4j;

/**
 * @author huangwh
 * @date 2018/11/11
 * @time 0:55
 */
public class ShareData {


    //开盘价
    private Double openPrice;

    //收盘价
    private Double closePrice;

    //涨幅额
    private Double riseFallAmount;

    //最低价
    private Double lowest;

    //最高价
    private Double highest;

    //成交量
    private Double volume;

    //成交额
    private Double turnover;



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

    @Override
    public String toString() {
        return "ShareData{" +
                ", openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", riseFallAmount=" + riseFallAmount +
                ", lowest=" + lowest +
                ", highest=" + highest +
                ", volume=" + volume +
                ", turnover=" + turnover +
                '}';
    }
}
