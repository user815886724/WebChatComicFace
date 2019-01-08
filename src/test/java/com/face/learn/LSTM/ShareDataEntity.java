package com.face.learn.LSTM;

/**
 * @author huangwh
 * @date 2018/11/20
 * @time 15:45
 */
public class ShareDataEntity {

    private String time;

    private String openPrice;

    private String closePrice;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }
}
