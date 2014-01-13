package com.jpower.cms.vo;

import java.sql.Timestamp;

public class InventoryApplicationVO {
	private String lob;
	private String subLob;
	private String subLobLabelEng;
	private String subLobLabelChin;
	private String category;
	private short categorySeq;
	private String categoryLabelEng;
	private String categoryLabelChin;
	private String categoryImagePath;
	private String series;
	private short seriesSeq;
	private String seriesLabelEng;
	private String seriesLabelChin;
	private String seriesImageSmallPath;
	private String seriesImageLargePath;
	private String subSeries;
	private short subSeriesSeq;
	private String subSeriesID;
	private String subSeriesImageSmallPath;
	private String subSeriesImageLargePath;
	private String tranAction;
	private String tranStatus;
	private int excelRowID;
	private String excelRowData;
	private Timestamp createDate;
	private Timestamp updateDate;
	private String createUser;
	private String updateUser;
	
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getSubLob() {
		return subLob;
	}
	public void setSubLob(String subLob) {
		this.subLob = subLob;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public short getCategorySeq() {
		return categorySeq;
	}
	public void setCategorySeq(short categorySeq) {
		this.categorySeq = categorySeq;
	}
	public String getCategoryLabelEng() {
		return categoryLabelEng;
	}
	public void setCategoryLabelEng(String categoryLabelEng) {
		this.categoryLabelEng = categoryLabelEng;
	}
	public String getCategoryLabelChin() {
		return categoryLabelChin;
	}
	public void setCategoryLabelChin(String categoryLabelChin) {
		this.categoryLabelChin = categoryLabelChin;
	}
	public String getCategoryImagePath() {
		return categoryImagePath;
	}
	public void setCategoryImagePath(String categoryImagePath) {
		this.categoryImagePath = categoryImagePath;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public short getSeriesSeq() {
		return seriesSeq;
	}
	public void setSeriesSeq(short seriesSeq) {
		this.seriesSeq = seriesSeq;
	}
	public String getSeriesLabelChin() {
		return seriesLabelChin;
	}
	public void setSeriesLabelChin(String seriesLabelChin) {
		this.seriesLabelChin = seriesLabelChin;
	}
	public String getSeriesLabelEng() {
		return seriesLabelEng;
	}
	public void setSeriesLabelEng(String seriesLabelEng) {
		this.seriesLabelEng = seriesLabelEng;
	}
	public String getSeriesImageSmallPath() {
		return seriesImageSmallPath;
	}
	public void setSeriesImageSmallPath(String seriesImagePath) {
		this.seriesImageSmallPath = seriesImagePath;
	}
	public String getSeriesImageLargePath() {
		return seriesImageLargePath;
	}
	public void setSeriesImageLargePath(String seriesImageLargePath) {
		this.seriesImageLargePath = seriesImageLargePath;
	}
	public String getSubSeries() {
		return subSeries;
	}
	public void setSubSeries(String subSeries) {
		this.subSeries = subSeries;
	}
	public short getSubSeriesSeq() {
		return subSeriesSeq;
	}
	public void setSubSeriesSeq(short subSeriesSeq) {
		this.subSeriesSeq = subSeriesSeq;
	}
	public String getSubSeriesID() {
		return subSeriesID;
	}
	public void setSubSeriesID(String subSeriesID) {
		this.subSeriesID = subSeriesID;
	}
	public String getSubSeriesImageSmallPath() {
		return subSeriesImageSmallPath;
	}
	public void setSubSeriesImageSmallPath(String subSeriesImageSmallPath) {
		this.subSeriesImageSmallPath = subSeriesImageSmallPath;
	}
	public String getSubSeriesImageLargePath() {
		return subSeriesImageLargePath;
	}
	public void setSubSeriesImageLargePath(String subSeriesImageLargePath) {
		this.subSeriesImageLargePath = subSeriesImageLargePath;
	}
	public String getTranAction() {
		return tranAction;
	}
	public void setTranAction(String tranAction) {
		this.tranAction = tranAction;
	}
	public String getTranStatus() {
		return tranStatus;
	}
	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}
	public int getExcelRowID() {
		return excelRowID;
	}
	public void setExcelRowID(int excelRowID) {
		this.excelRowID = excelRowID;
	}
	public String getExcelRowData() {
		return excelRowData;
	}
	public void setExcelRowData(String excelRowData) {
		this.excelRowData = excelRowData;
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
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getSubLobLabelEng() {
		return subLobLabelEng;
	}
	public void setSubLobLabelEng(String subLobLabelEng) {
		this.subLobLabelEng = subLobLabelEng;
	}
	public String getSubLobLabelChin() {
		return subLobLabelChin;
	}
	public void setSubLobLabelChin(String subLobLabelChin) {
		this.subLobLabelChin = subLobLabelChin;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getLob());
		sb.append("|");
		sb.append(getSubLob());
		sb.append("|");
		sb.append(getSubLobLabelEng());
		sb.append("|");
		sb.append(getSubLobLabelChin());
		sb.append("|");
		sb.append(getCategory());
		sb.append("|");
		sb.append(getCategorySeq());
		sb.append("|");
		sb.append(getCategoryLabelEng());
		sb.append("|");
		sb.append(getCategoryLabelChin());
		sb.append("|");
		sb.append(getCategoryImagePath());
		sb.append("|");
		sb.append(getTranAction());
		sb.append("|");
		sb.append(getSeries());
		sb.append("|");
		sb.append(getSeriesSeq());
		sb.append("|");
		sb.append(getSeriesLabelEng());
		sb.append("|");
		sb.append(getSeriesLabelChin());
		sb.append("|");
		sb.append(getSeriesImageSmallPath());
		sb.append("|");
		sb.append(getSeriesImageLargePath());
		sb.append("|");
		sb.append(getSubSeries());
		sb.append("|");
		sb.append(getSubSeriesSeq());
		sb.append("|");
		sb.append(getSubSeriesID());
		sb.append("|");
		sb.append(getSubSeriesImageSmallPath());
		sb.append("|");
		sb.append(getSubSeriesImageLargePath());
		
		return sb.toString();
	}
}
