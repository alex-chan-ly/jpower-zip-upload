package com.jpower.cms.upload.excel;

import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;

import com.jpower.cms.dao.InventoryApplicationDetailVO;
import com.jpower.cms.dao.InventoryApplicationVO;
import com.jpower.cms.upload.common.Util;

public class InventoryApplicationDetailProcessor {
	
	public static List<InventoryApplicationDetailVO> processApplicationSheet(Sheet s) {
		List<InventoryApplicationDetailVO> appVOList = new ArrayList<InventoryApplicationDetailVO>();
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
	
	public static InventoryApplicationDetailVO convertToVo(int rowNum, Cell[] row) {
		int columnIndex = 0;
		InventoryApplicationDetailVO vo = new InventoryApplicationDetailVO();
		
		vo.setTranAction(row[columnIndex++].getContents());
		vo.setSubSeriesId(row[columnIndex++].getContents());
		vo.setSeries(row[columnIndex++].getContents());
		vo.setAvailableSize(row[columnIndex++].getContents());
		vo.setTileThickness(row[columnIndex++].getContents());
		vo.setColor(row[columnIndex++].getContents());
		vo.setFinishing(row[columnIndex++].getContents());
		vo.setApplication(row[columnIndex++].getContents());
		vo.setRemarks1(row[columnIndex++].getContents());
		vo.setExcelRowID(rowNum + Util.EXCEL_HEADER_LINE_NUM);
		vo.setExcelRowData(vo.toString());

		System.out.println(vo.toString());
		return vo;
	}
}
