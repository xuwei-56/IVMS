package com.IVMS.model;

public class Warehouse {
    private String wid;

    private String wclassify;

    private Integer wstatus;

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid == null ? null : wid.trim();
    }

    public String getWclassify() {
        return wclassify;
    }

    public void setWclassify(String wclassify) {
        this.wclassify = wclassify == null ? null : wclassify.trim();
    }

    public Integer getWstatus() {
        return wstatus;
    }

    public void setWstatus(Integer wstatus) {
        this.wstatus = wstatus;
    }
}