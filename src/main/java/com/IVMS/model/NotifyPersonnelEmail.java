package com.IVMS.model;

import java.util.Date;

public class NotifyPersonnelEmail {
    private Integer npeid;

    private String cfid;

    private String npenotifyemail;

    private Date npenotifytime;
    
    private Integer npestyle;
    

	public Integer getNpeid() {
		return npeid;
	}

	public void setNpeid(Integer npeid) {
		this.npeid = npeid;
	}

	public String getCfid() {
		return cfid;
	}

	public void setCfid(String cfid) {
		this.cfid = cfid;
	}

	public String getNpenotifyemail() {
		return npenotifyemail;
	}

	public void setNpenotifyemail(String npenotifyemail) {
		this.npenotifyemail = npenotifyemail;
	}

	public Date getNpenotifytime() {
		return npenotifytime;
	}

	public void setNpenotifytime(Date npenotifytime) {
		this.npenotifytime = npenotifytime;
	}

	public Integer getNpestyle() {
		return npestyle;
	}

	public void setNpestyle(Integer npestyle) {
		this.npestyle = npestyle;
	}

    
}