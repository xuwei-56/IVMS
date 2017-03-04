package com.IVMS.model;

public class CheckingForm {
    private String cfid;

    private Integer lid;

    private Integer cid;

    private Integer pid;

    private String wid;

    private Integer claid;

    private Integer ccid;

    private Integer cfmovep;

    private String cfphonenum;

    private String cfemail;

    private String cfcomponentname;

    private String cfcomponentid;

    private Integer cfcomponentnum;

    private String cfremark;

    private Integer cfstatus;

    private Integer cfurgentstatus;

    public String getCfid() {
        return cfid;
    }

    public void setCfid(String cfid) {
        this.cfid = cfid == null ? null : cfid.trim();
    }

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid == null ? null : wid.trim();
    }

    public Integer getClaid() {
        return claid;
    }

    public void setClaid(Integer claid) {
        this.claid = claid;
    }

    public Integer getCcid() {
        return ccid;
    }

    public void setCcid(Integer ccid) {
        this.ccid = ccid;
    }

    public Integer getCfmovep() {
        return cfmovep;
    }

    public void setCfmovep(Integer cfmovep) {
        this.cfmovep = cfmovep;
    }

    public String getCfphonenum() {
        return cfphonenum;
    }

    public void setCfphonenum(String cfphonenum) {
        this.cfphonenum = cfphonenum == null ? null : cfphonenum.trim();
    }

    public String getCfemail() {
        return cfemail;
    }

    public void setCfemail(String cfemail) {
        this.cfemail = cfemail == null ? null : cfemail.trim();
    }

    public String getCfcomponentname() {
        return cfcomponentname;
    }

    public void setCfcomponentname(String cfcomponentname) {
        this.cfcomponentname = cfcomponentname == null ? null : cfcomponentname.trim();
    }

    public String getCfcomponentid() {
        return cfcomponentid;
    }

    public void setCfcomponentid(String cfcomponentid) {
        this.cfcomponentid = cfcomponentid == null ? null : cfcomponentid.trim();
    }

    public Integer getCfcomponentnum() {
        return cfcomponentnum;
    }

    public void setCfcomponentnum(Integer cfcomponentnum) {
        this.cfcomponentnum = cfcomponentnum;
    }

    public String getCfremark() {
        return cfremark;
    }

    public void setCfremark(String cfremark) {
        this.cfremark = cfremark == null ? null : cfremark.trim();
    }

    public Integer getCfstatus() {
        return cfstatus;
    }

    public void setCfstatus(Integer cfstatus) {
        this.cfstatus = cfstatus;
    }

    public Integer getCfurgentstatus() {
        return cfurgentstatus;
    }

    public void setCfurgentstatus(Integer cfurgentstatus) {
        this.cfurgentstatus = cfurgentstatus;
    }
}