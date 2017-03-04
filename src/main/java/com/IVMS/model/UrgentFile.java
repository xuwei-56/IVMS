package com.IVMS.model;

public class UrgentFile {
    private Integer ufid;

    private String cfid;

    private String ufname;

    public Integer getUfid() {
        return ufid;
    }

    public void setUfid(Integer ufid) {
        this.ufid = ufid;
    }

    public String getCfid() {
        return cfid;
    }

    public void setCfid(String cfid) {
        this.cfid = cfid == null ? null : cfid.trim();
    }

    public String getUfname() {
        return ufname;
    }

    public void setUfname(String ufname) {
        this.ufname = ufname == null ? null : ufname.trim();
    }
}