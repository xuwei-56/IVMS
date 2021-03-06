package com.IVMS.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class CheckingToolsRecord {
    private Integer ctrid;

    private String ctid;

    private String ctrnum;
    
    private String ctrmovecp;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date ctrmovetime;

    private Date ctrchecktime;

    private Date ctrchecknexttime;

    private String ctrcheckcontent;

    private String ctrcheckvalue;

    private String ctrchecktools;

    private Integer ctrcheckresult;

    private Integer ctracceptresult;

    private Integer ctrremark;
    
    private String ctrcheckman;
    
    private List<NotifyPersonnelEmail> notifyPersonnelEmails;
    
    private Integer ctisagree;
    

	public Integer getCtrid() {
		return ctrid;
	}

	public void setCtrid(Integer ctrid) {
		this.ctrid = ctrid;
	}

	public String getCtid() {
		return ctid;
	}

	public void setCtid(String ctid) {
		this.ctid = ctid;
	}

	public String getCtrnum() {
		return ctrnum;
	}

	public void setCtrnum(String ctrnum) {
		this.ctrnum = ctrnum;
	}

	public String getCtrmovecp() {
		return ctrmovecp;
	}

	public void setCtrmovecp(String ctrmovecp) {
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
		this.ctrcheckcontent = ctrcheckcontent;
	}

	public String getCtrcheckvalue() {
		return ctrcheckvalue;
	}

	public void setCtrcheckvalue(String ctrcheckvalue) {
		this.ctrcheckvalue = ctrcheckvalue;
	}

	public String getCtrchecktools() {
		return ctrchecktools;
	}

	public void setCtrchecktools(String ctrchecktools) {
		this.ctrchecktools = ctrchecktools;
	}

	public Integer getCtrcheckresult() {
		return ctrcheckresult;
	}

	public void setCtrcheckresult(Integer ctrcheckresult) {
		this.ctrcheckresult = ctrcheckresult;
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

	public String getCtrcheckman() {
		return ctrcheckman;
	}

	public void setCtrcheckman(String ctrcheckman) {
		this.ctrcheckman = ctrcheckman;
	}

	public List<NotifyPersonnelEmail> getNotifyPersonnelEmails() {
		return notifyPersonnelEmails;
	}

	public void setNotifyPersonnelEmails(List<NotifyPersonnelEmail> notifyPersonnelEmails) {
		this.notifyPersonnelEmails = notifyPersonnelEmails;
	}

	public Integer getCtisagree() {
		return ctisagree;
	}

	public void setCtisagree(Integer ctisagree) {
		this.ctisagree = ctisagree;
	}

}