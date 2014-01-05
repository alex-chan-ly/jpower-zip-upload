package com.jpower.cms.upload.excel;

import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;

import com.jpower.cms.dao.InventoryApplicationVO;
import com.jpower.cms.upload.common.Util;

public class InventoryApplicationProcessor {
	
	public static List<InventoryApplicationVO> processApplicationSheet(Sheet s) {
		List<InventoryApplicationVO> appVOList = new ArrayList<InventoryApplicationVO>();
		Cell[] row = null;
		for (int i = 0; i < s.getRows(); i++) {
			row = s.getRow(i);
			System.out.print("[" + +i + "] : ");
			if (row.length > 0) {
				for (int j = 0; j < row.length; j++) {
					System.out.print(row[j].getContents() + ", ");
				}
			}
			System.out.println("\n");
			
			if (i > 0) {
				appVOList.add(convertToVo(i, row));
			}
		}
		
		return appVOList;
	}
	
	public static InventoryApplicationVO convertToVo(int rowNum, Cell[] row) {
		int columnIndex = 0;
		InventoryApplicationVO vo = new InventoryApplicationVO();
		
		vo.setLob(row[columnIndex++].getContents());
		vo.setSubLob(row[columnIndex++].getContents());
		vo.setCategory(row[columnIndex++].getContents());
		vo.setCategorySeq(Short.parseShort(row[columnIndex++].getContents()));
		vo.setCategoryLabelEng(row[columnIndex++].getContents());
		vo.setCategoryLabelChin(row[columnIndex++].getContents());
		vo.setCategoryImagePath(row[columnIndex++].getContents());
		vo.setTranAction(row[columnIndex++].getContents());
		vo.setSeries(row[columnIndex++].getContents());
		vo.setSeriesSeq(Short.parseShort(row[columnIndex++].getContents()));
		vo.setSeriesLabelEng(row[columnIndex++].getContents());
		vo.setSeriesLabelChin(row[columnIndex++].getContents());
		vo.setSeriesImageSmallPath(row[columnIndex++].getContents());
		vo.setSeriesImageLargePath(row[columnIndex++].getContents());
		vo.setSubSeries(row[columnIndex++].getContents());
		vo.setSubSeriesSeq(Short.parseShort(row[columnIndex++].getContents()));
		vo.setSubSeriesID(row[columnIndex++].getContents());
		vo.setSubSeriesImageSmallPath(row[columnIndex++].getContents());
		vo.setSubSeriesImageLargePath(row[columnIndex++].getContents());
		vo.setExcelRowID(rowNum + Util.EXCEL_HEADER_LINE_NUM);
		vo.setExcelRowData(vo.toString());
		
		System.out.println(vo.toString());
		return vo;
	}
}
