package com.IVMS.model;

import java.util.Date;

public class CheckingToolsRecord {
    private Integer ctrid;

    private Integer ctid;

    private Integer ctrnum;

    private Integer ctrmovecp;

    private Date ctrmovetime;

    private Date ctrchecktime;

    private Date ctrchecknexttime;

    private String ctrcheckcontent;

    private String ctrckeckvalue;

    private String ctrckecktools;

    private Integer ctrckeckresult;

    private Integer ctracceptresult;

    private Integer ctrremark;

    public Integer getCtrid() {
        return ctrid;
    }

    public void setCtrid(Integer ctrid) {
        this.ctrid = ctrid;
    }

    public Integer getCtid() {
        return ctid;
    }

    public void setCtid(Integer ctid) {
        this.ctid = ctid;
    }

    public Integer getCtrnum() {
        return ctrnum;
    }

    public void setCtrnum(Integer ctrnum) {
        this.ctrnum = ctrnum;
    }

    public Integer getCtrmovecp() {
        return ctrmovecp;
    }

    public void setCtrmovecp(Integer ctrmovecp) {
        this.ctrmovecp = ctrmovecp;
    }

    public Date getCtrmovetime() {
        return ctrmovetime;
    }

    public void setCtrmovetime(Date ctrmovetime) {
        this.ctrmovetime = ctrmovetime;
    }

    public Date getCtrchecktime() {
        return ctrchecktime;
    }

    public void setCtrchecktime(Date ctrchecktime) {
        this.ctrchecktime = ctrchecktime;
    }

    public Date getCtrchecknexttime() {
        return ctrchecknexttime;
    }

    public void setCtrchecknexttime(Date ctrchecknexttime) {
        this.ctrchecknexttime = ctrchecknexttime;
    }

    public String getCtrcheckcontent() {
        return ctrcheckcontent;
    }

    public void setCtrcheckcontent(String ctrcheckcontent) {
        this.ctrcheckcontent = ctrcheckcontent == null ? null : ctrcheckcontent.trim();
    }

    public String getCtrckeckvalue() {
        return ctrckeckvalue;
    }

    public void setCtrckeckvalue(String ctrckeckvalue) {
        this.ctrckeckvalue = ctrckeckvalue == null ? null : ctrckeckvalue.trim();
    }

    public String getCtrckecktools() {
        return ctrckecktools;
    }

    public void setCtrckecktools(String ctrckecktools) {
        this.ctrckecktools = ctrckecktools == null ? null : ctrckecktools.trim();
    }

    public Integer getCtrckeckresult() {
        return ctrckeckresult;
    }

    public void setCtrckeckresult(Integer ctrckeckresult) {
        this.ctrckeckresult = ctrckeckresult;
    }

    public Integer getCtracceptresult() {
        return ctracceptresult;
    }

    public void setCtracceptresult(Integer ctracceptresult) {
        this.ctracceptresult = ctracceptresult;
    }

    public Integer getCtrremark() {
        return ctrremark;
    }

    public void setCtrremark(Integer ctrremark) {
        this.ctrremark = ctrremark;
    }
}