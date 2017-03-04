package com.IVMS.model;

public class CheckingClassify {
    private Integer ccid;

    private Integer claid;

    private String ccname;

    public Integer getCcid() {
        return ccid;
    }

    public void setCcid(Integer ccid) {
        this.ccid = ccid;
    }

    public Integer getClaid() {
        return claid;
    }

    public void setClaid(Integer claid) {
        this.claid = claid;
    }

    public String getCcname() {
        return ccname;
    }

    public void setCcname(String ccname) {
        this.ccname = ccname == null ? null : ccname.trim();
    }
}