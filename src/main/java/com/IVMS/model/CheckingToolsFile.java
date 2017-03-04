package com.IVMS.model;

public class CheckingToolsFile {
    private Integer ctfid;

    private Integer ctid;

    private String ctfname;

    public Integer getCtfid() {
        return ctfid;
    }

    public void setCtfid(Integer ctfid) {
        this.ctfid = ctfid;
    }

    public Integer getCtid() {
        return ctid;
    }

    public void setCtid(Integer ctid) {
        this.ctid = ctid;
    }

    public String getCtfname() {
        return ctfname;
    }

    public void setCtfname(String ctfname) {
        this.ctfname = ctfname == null ? null : ctfname.trim();
    }
}