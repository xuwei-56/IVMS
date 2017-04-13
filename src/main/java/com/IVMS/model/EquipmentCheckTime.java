package com.IVMS.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EquipmentCheckTime {
    private Integer ectid;

    private Integer eid;

    private Date ectime;

    private Date ecnexttime;

    public Integer getEctid() {
        return ectid;
    }

    public void setEctid(Integer ectid) {
        this.ectid = ectid;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Date getEctime() {
        return ectime;
    }

    public void setEctime(Date ectime) {
        this.ectime = ectime;
    }

    public Date getEcnexttime() {
        return ecnexttime;
    }

    public void setEcnexttime(Date ecnexttime) {
        this.ecnexttime = ecnexttime;
    }
}