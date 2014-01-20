package com.jpower.cms.vo;

import java.sql.Timestamp;

public class InventoryApplicationDetailVO {
	private String subSeriesId;
	private String name;
	private String series;
	private String descriptionEng;
	private String descriptionChin;
	private String availableSize;
	private String availableSizeChin;
	private String tileThickness;
	private String tileThicknessChin;
	private String color;
	private String colorChin;
	private String finishing;
	private String finishingChin;
	private String application;
	private String applicationChin;
	private String remarks1;
	private String remarks1Chin;
	private String remarks2;
	private String remarks2Chin;
	private String remarks3;
	private String remarks3Chin;
	private String tranAction;
	private String tranStatus;
	private int excelRowID;
	private String excelRowData;
	private Timestamp createDate;
	private Timestamp updateDate;
	private String createUser;
	private String updateUser;
	public String getSubSeriesId() {
		return subSeriesId;
	}
	public void setSubSeriesId(String subSeriesId) {
		this.subSeriesId = subSeriesId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getAvailableSize() {
		return availableSize;
	}
	public void setAvailableSize(String availableSize) {
		this.availableSize = availableSize;
	}
	public String getTileThickness() {
		return tileThickness;
	}
	public void setTileThickness(String tileThickness) {
		this.tileThickness = tileThickness;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getFinishing() {
		return finishing;
	}
	public void setFinishing(String finishing) {
		this.finishing = finishing;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getRemarks1() {
		return remarks1;
	}
	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}
	public String getRemarks2() {
		return remarks2;
	}
	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}
	public String getRemarks3() {
		return remarks3;
	}
	public void setRemarks3(String remarks3) {
		this.remarks3 = remarks3;
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
	public String getDescriptionEng() {
		return descriptionEng;
	}
	public void setDescriptionEng(String description) {
		this.descriptionEng = description;
	}
	
	public String getDescriptionChin() {
		return descriptionChin;
	}
	public void setDescriptionChin(String descriptionChin) {
		this.descriptionChin = descriptionChin;
	}
	public String getAvailableSizeChin() {
		return availableSizeChin;
	}
	public void setAvailableSizeChin(String availableSizeChin) {
		this.availableSizeChin = availableSizeChin;
	}
	public String getTileThicknessChin() {
		return tileThicknessChin;
	}
	public void setTileThicknessChin(String tileThicknessChin) {
		this.tileThicknessChin = tileThicknessChin;
	}
	public String getColorChin() {
		return colorChin;
	}
	public void setColorChin(String colorChin) {
		this.colorChin = colorChin;
	}
	public String getFinishingChin() {
		return finishingChin;
	}
	public void setFinishingChin(String finishingChin) {
		this.finishingChin = finishingChin;
	}
	public String getApplicationChin() {
		return applicationChin;
	}
	public void setApplicationChin(String applicationChin) {
		this.applicationChin = applicationChin;
	}
	public String getRemarks1Chin() {
		return remarks1Chin;
	}
	public void setRemarks1Chin(String remarks1Chin) {
		this.remarks1Chin = remarks1Chin;
	}
	public String getRemarks2Chin() {
		return remarks2Chin;
	}
	public void setRemarks2Chin(String remarks2Chin) {
		this.remarks2Chin = remarks2Chin;
	}
	public String getRemarks3Chin() {
		return remarks3Chin;
	}
	public void setRemarks3Chin(String remarks3Chin) {
		this.remarks3Chin = remarks3Chin;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getTranAction());
		sb.append("|");
		sb.append(getSubSeriesId());
		sb.append("|");
		sb.append(getSeries());
		sb.append("|");
		sb.append(getName());
		sb.append("|");
		sb.append(getDescriptionEng());
		sb.append("|");
		sb.append(getDescriptionChin());
		sb.append("|");
		sb.append(getAvailableSize());
		sb.append("|");
		sb.append(getAvailableSizeChin());
		sb.append("|");
		sb.append(getTileThickness());
		sb.append("|");
		sb.append(getTileThicknessChin());
		sb.append("|");
		sb.append(getColor());
		sb.append("|");
		sb.append(getColorChin());
		sb.append("|");
		sb.append(getFinishing());
		sb.append("|");
		sb.append(getFinishingChin());
		sb.append("|");
		sb.append(getApplication());
		sb.append("|");
		sb.append(getApplicationChin());
		sb.append("|");
		sb.append(getRemarks1());
		sb.append("|");
		sb.append(getRemarks1Chin());
		
		return sb.toString();
	}
}
