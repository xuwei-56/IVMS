package com.IVMS.model;

import java.util.Date;
import java.util.List;


public class CheckingForm {
    private String cfid;

    private Integer lid;

    private Integer cid;

    private Integer pid;

    private String wid;

    private Integer claid;

    private Integer ccid;

    private String cfmovep;

    private String cfphonenum;

    private String cfemail;

    private String cfcomponentname;

    private String cfcomponentid;

    private Integer cfcomponentnum;

    private Integer cfchecknum;

    private String cfremark;

    private Integer cfstatus;

    private Integer cfurgentstatus;

    private String cfremarkfile;

    private String cfreportfile;
    
    private Date cftime;
    
    private String cfreply;
    
    private String cfreplyreport;
    
    private String cfcheckman;
    
    private Line line;
    
    private Cell cell;
    
    private Project project;
    
    private Classify classify;
    
    private CheckingClassify checkingClassify;
    
    private List<NotifyPersonnelEmail>notifyPersonnelEmail;
    
    private UrgentFile urgentFile;
    
    private Warehouse warehouse;

    
	public String getCfid() {
		return cfid;
	}

	public void setCfid(String cfid) {
		this.cfid = cfid;
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
		this.wid = wid;
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

	public String getCfmovep() {
		return cfmovep;
	}

	public void setCfmovep(String cfmovep) {
		this.cfmovep = cfmovep;
	}

	public String getCfphonenum() {
		return cfphonenum;
	}

	public void setCfphonenum(String cfphonenum) {
		this.cfphonenum = cfphonenum;
	}

	public String getCfemail() {
		return cfemail;
	}

	public void setCfemail(String cfemail) {
		this.cfemail = cfemail;
	}

	public String getCfcomponentname() {
		return cfcomponentname;
	}

	public void setCfcomponentname(String cfcomponentname) {
		this.cfcomponentname = cfcomponentname;
	}

	public String getCfcomponentid() {
		return cfcomponentid;
	}

	public void setCfcomponentid(String cfcomponentid) {
		this.cfcomponentid = cfcomponentid;
	}

	public Integer getCfcomponentnum() {
		return cfcomponentnum;
	}

	public void setCfcomponentnum(Integer cfcomponentnum) {
		this.cfcomponentnum = cfcomponentnum;
	}

	public Integer getCfchecknum() {
		return cfchecknum;
	}

	public void setCfchecknum(Integer cfchecknum) {
		this.cfchecknum = cfchecknum;
	}

	public String getCfremark() {
		return cfremark;
	}

	public void setCfremark(String cfremark) {
		this.cfremark = cfremark;
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

	public String getCfremarkfile() {
		return cfremarkfile;
	}

	public void setCfremarkfile(String cfremarkfile) {
		this.cfremarkfile = cfremarkfile;
	}

	public String getCfreportfile() {
		return cfreportfile;
	}

	public void setCfreportfile(String cfreportfile) {
		this.cfreportfile = cfreportfile;
	}

	public Date getCftime() {
		return cftime;
	}

	public void setCftime(Date cftime) {
		this.cftime = cftime;
	}

	public String getCfreply() {
		return cfreply;
	}

	public void setCfreply(String cfreply) {
		this.cfreply = cfreply;
	}

	public String getCfreplyreport() {
		return cfreplyreport;
	}

	public void setCfreplyreport(String cfreplyreport) {
		this.cfreplyreport = cfreplyreport;
	}

	public String getCfcheckman() {
		return cfcheckman;
	}

	public void setCfcheckman(String cfcheckman) {
		this.cfcheckman = cfcheckman;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Classify getClassify() {
		return classify;
	}

	public void setClassify(Classify classify) {
		this.classify = classify;
	}

	public CheckingClassify getCheckingClassify() {
		return checkingClassify;
	}

	public void setCheckingClassify(CheckingClassify checkingClassify) {
		this.checkingClassify = checkingClassify;
	}

	public List<NotifyPersonnelEmail> getNotifyPersonnelEmail() {
		return notifyPersonnelEmail;
	}

	public void setNotifyPersonnelEmail(List<NotifyPersonnelEmail> notifyPersonnelEmail) {
		this.notifyPersonnelEmail = notifyPersonnelEmail;
	}

	public UrgentFile getUrgentFile() {
		return urgentFile;
	}

	public void setUrgentFile(UrgentFile urgentFile) {
		this.urgentFile = urgentFile;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

    
    
}