package com.jpower.cms.vo;

import java.sql.Timestamp;
import java.util.Date;


public class LogVO {
	private String refIdx;
	private String severity;
	private String category;
	private String logMessage;
	private String remarks1;
	private Timestamp createDate;
	private Timestamp updateDate;
	
	
	public String getRefIdx() {
		return refIdx;
	}
	public void setRefIdx(String refIdx) {
		this.refIdx = refIdx;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLogMessage() {
		return logMessage;
	}
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	public String getRemarks1() {
		return remarks1;
	}
	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getRefIdx());
		sb.append("|");
		sb.append(getSeverity());
		sb.append("|");
		sb.append(getCategory());
		sb.append("|");
		sb.append(getLogMessage());
		sb.append("|");
		sb.append(getRemarks1());
		sb.append("|");
		sb.append(getCreateDate());
		sb.append("|");
		sb.append(getUpdateDate());
		
		return sb.toString();
	}
}
