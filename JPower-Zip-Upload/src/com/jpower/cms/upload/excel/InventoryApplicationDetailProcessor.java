package com.jpower.cms.upload.excel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.jpower.cms.upload.common.Util;
import com.jpower.cms.vo.InventoryApplicationDetailVO;
import com.jpower.cms.vo.InventoryApplicationVO;

public class InventoryApplicationDetailProcessor {
	
	private static String[] headerRowLabel = {"Action", "Sub-Series-ID", "Series"
		, "Name", "Description-Eng", "Description-Chin", "Available-Sizes"
		, "Available-Sizes-Chin", "Tile-Thickness", "Tile-Thickness-Chin"
		, "Colour", "Colour-Chin", "Finishing", "Finishing-Chin"
		, "Application", "Application-Chin", "Remarks", "Remarks-Chin"};
	
	
	private static void generateInventoryApplicationDetailHeaderRow(WritableSheet ws) {
		for (int i = 0; i < headerRowLabel.length; i++) {
			Label lbl = new Label(i, 0, headerRowLabel[i]);
			try {
				ws.addCell(lbl);
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}
	
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
		vo.setName(row[columnIndex++].getContents());
		vo.setDescriptionEng(row[columnIndex++].getContents());
		vo.setDescriptionChin(row[columnIndex++].getContents());
		vo.setAvailableSize(row[columnIndex++].getContents());
		vo.setAvailableSizeChin(row[columnIndex++].getContents());
		vo.setTileThickness(row[columnIndex++].getContents());
		vo.setTileThicknessChin(row[columnIndex++].getContents());
		vo.setColor(row[columnIndex++].getContents());
		vo.setColorChin(row[columnIndex++].getContents());
		vo.setFinishing(row[columnIndex++].getContents());
		vo.setFinishingChin(row[columnIndex++].getContents());
		vo.setApplication(row[columnIndex++].getContents());
		vo.setApplicationChin(row[columnIndex++].getContents());
		vo.setRemarks1(row[columnIndex++].getContents());
		vo.setRemarks1Chin(row[columnIndex++].getContents());
		vo.setExcelRowID(rowNum + Util.EXCEL_HEADER_LINE_NUM);
		vo.setExcelRowData(vo.toString());

		System.out.println(vo.toString());
		return vo;
	}
	
	public static int generateInventoryApplicationDetailDataRow(WritableSheet ws, List<InventoryApplicationDetailVO> voList) {
		int rtnCode = 0;
		int rowNum = 1;
		
		generateInventoryApplicationDetailHeaderRow(ws);
		
		for (Iterator<InventoryApplicationDetailVO> i = voList.iterator(); i.hasNext();) {
			InventoryApplicationDetailVO vo = i.next();

			try {
				Label lbl1 = new Label(0, rowNum, vo.getTranAction());
				ws.addCell(lbl1);
				Label lbl2 = new Label(1, rowNum, vo.getSubSeriesId());
				ws.addCell(lbl2);
				Label lbl3 = new Label(2, rowNum, vo.getSeries());
				ws.addCell(lbl3);
				Label lbl4 = new Label(3, rowNum, vo.getName());
				ws.addCell(lbl4);
				Label lbl5 = new Label(4, rowNum, vo.getDescriptionEng());
				ws.addCell(lbl5);
				Label lbl6 = new Label(5, rowNum, vo.getDescriptionChin());
				ws.addCell(lbl6);
				Label lbl7 = new Label(6, rowNum, vo.getAvailableSize());
				ws.addCell(lbl7);
				Label lbl8 = new Label(7, rowNum, vo.getAvailableSizeChin());
				ws.addCell(lbl8);
				Label lbl9 = new Label(8, rowNum, vo.getTileThickness());
				ws.addCell(lbl9);
				Label lbl10 = new Label(9, rowNum, vo.getTileThicknessChin());
				ws.addCell(lbl10);
				Label lbl11 = new Label(10, rowNum, vo.getColor());
				ws.addCell(lbl11);
				Label lbl12 = new Label(11, rowNum, vo.getColorChin());
				ws.addCell(lbl12);
				Label lbl13 = new Label(12, rowNum, vo.getFinishing());
				ws.addCell(lbl13);
				Label lbl14 = new Label(13, rowNum, vo.getFinishingChin());
				ws.addCell(lbl14);									
				Label lbl15 = new Label(14, rowNum, vo.getApplication());
				ws.addCell(lbl15);
				Label lbl16 = new Label(15, rowNum, vo.getApplicationChin());
				ws.addCell(lbl16);
				Label lbl17 = new Label(16, rowNum, vo.getRemarks1());
				ws.addCell(lbl17);
				Label lbl18 = new Label(17, rowNum, vo.getRemarks1Chin());
				ws.addCell(lbl18);													
			} catch (RowsExceededException e) {
				e.printStackTrace();
				rtnCode = -1;
			} catch (WriteException e) {
				e.printStackTrace();
				rtnCode = -1;
			}
			rowNum++;
		}
		return rtnCode;
	}
}
