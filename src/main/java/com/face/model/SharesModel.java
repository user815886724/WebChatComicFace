package com.face.model;

/**
 * @author huangwh
 * @date 2018/11/3
 * @time 17:56
 */
public class SharesModel {

    private String id;

    private String code;

    private String codeName;

    private String timeDesc;

    public SharesModel() {
    }

    public SharesModel(String id, String code, String codeName, String timeDesc) {
        this.id = id;
        this.code = code;
        this.codeName = codeName;
        this.timeDesc = timeDesc;
    }

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

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }
}
