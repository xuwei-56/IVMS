package com.IVMS.model;

public class Equipment {
    private Integer eid;

    private String ename;

    private Integer cid;

    private String eworker;

    private Integer echeckcycle;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getEworker() {
        return eworker;
    }

    public void setEworker(String eworker) {
        this.eworker = eworker == null ? null : eworker.trim();
    }

    public Integer getEcheckcycle() {
        return echeckcycle;
    }

    public void setEcheckcycle(Integer echeckcycle) {
        this.echeckcycle = echeckcycle;
    }
}