package com.IVMS.model;

import java.util.Date;

public class CheckingTools {
    private Integer ctid;

    private String ctuseitem;

    private String ctcheckprogram;

    private String ctname;

    private String ctproducer;

    private String ctproductionnum;

    private String ctnorms;

    private String ctprocision;

    private String ctresolation;

    private String ctassetnum;

    private String ctuseline;

    private String ctusestation;

    private Date ctusetime;

    private Integer ctcheckway;

    private Integer ctcheckcycle;

    private Integer ctmsa;

    private String ctoriginalnum;

    private Integer ctsize;

    private String ctchecktemperature;

    private String ctcheckhumidiry;

    public Integer getCtid() {
        return ctid;
    }

    public void setCtid(Integer ctid) {
        this.ctid = ctid;
    }

    public String getCtuseitem() {
        return ctuseitem;
    }

    public void setCtuseitem(String ctuseitem) {
        this.ctuseitem = ctuseitem == null ? null : ctuseitem.trim();
    }

    public String getCtcheckprogram() {
        return ctcheckprogram;
    }

    public void setCtcheckprogram(String ctcheckprogram) {
        this.ctcheckprogram = ctcheckprogram == null ? null : ctcheckprogram.trim();
    }

    public String getCtname() {
        return ctname;
    }

    public void setCtname(String ctname) {
        this.ctname = ctname == null ? null : ctname.trim();
    }

    public String getCtproducer() {
        return ctproducer;
    }

    public void setCtproducer(String ctproducer) {
        this.ctproducer = ctproducer == null ? null : ctproducer.trim();
    }

    public String getCtproductionnum() {
        return ctproductionnum;
    }

    public void setCtproductionnum(String ctproductionnum) {
        this.ctproductionnum = ctproductionnum == null ? null : ctproductionnum.trim();
    }

    public String getCtnorms() {
        return ctnorms;
    }

    public void setCtnorms(String ctnorms) {
        this.ctnorms = ctnorms == null ? null : ctnorms.trim();
    }

    public String getCtprocision() {
        return ctprocision;
    }

    public void setCtprocision(String ctprocision) {
        this.ctprocision = ctprocision == null ? null : ctprocision.trim();
    }

    public String getCtresolation() {
        return ctresolation;
    }

    public void setCtresolation(String ctresolation) {
        this.ctresolation = ctresolation == null ? null : ctresolation.trim();
    }

    public String getCtassetnum() {
        return ctassetnum;
    }

    public void setCtassetnum(String ctassetnum) {
        this.ctassetnum = ctassetnum == null ? null : ctassetnum.trim();
    }

    public String getCtuseline() {
        return ctuseline;
    }

    public void setCtuseline(String ctuseline) {
        this.ctuseline = ctuseline == null ? null : ctuseline.trim();
    }

    public String getCtusestation() {
        return ctusestation;
    }

    public void setCtusestation(String ctusestation) {
        this.ctusestation = ctusestation == null ? null : ctusestation.trim();
    }

    public Date getCtusetime() {
        return ctusetime;
    }

    public void setCtusetime(Date ctusetime) {
        this.ctusetime = ctusetime;
    }

    public Integer getCtcheckway() {
        return ctcheckway;
    }

    public void setCtcheckway(Integer ctcheckway) {
        this.ctcheckway = ctcheckway;
    }

    public Integer getCtcheckcycle() {
        return ctcheckcycle;
    }

    public void setCtcheckcycle(Integer ctcheckcycle) {
        this.ctcheckcycle = ctcheckcycle;
    }

    public Integer getCtmsa() {
        return ctmsa;
    }

    public void setCtmsa(Integer ctmsa) {
        this.ctmsa = ctmsa;
    }

    public String getCtoriginalnum() {
        return ctoriginalnum;
    }

    public void setCtoriginalnum(String ctoriginalnum) {
        this.ctoriginalnum = ctoriginalnum == null ? null : ctoriginalnum.trim();
    }

    public Integer getCtsize() {
        return ctsize;
    }

    public void setCtsize(Integer ctsize) {
        this.ctsize = ctsize;
    }

    public String getCtchecktemperature() {
        return ctchecktemperature;
    }

    public void setCtchecktemperature(String ctchecktemperature) {
        this.ctchecktemperature = ctchecktemperature == null ? null : ctchecktemperature.trim();
    }

    public String getCtcheckhumidiry() {
        return ctcheckhumidiry;
    }

    public void setCtcheckhumidiry(String ctcheckhumidiry) {
        this.ctcheckhumidiry = ctcheckhumidiry == null ? null : ctcheckhumidiry.trim();
    }
}