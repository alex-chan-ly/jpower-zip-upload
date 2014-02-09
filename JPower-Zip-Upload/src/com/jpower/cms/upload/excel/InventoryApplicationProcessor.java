package com.jpower.cms.upload.excel;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.jpower.cms.dao.CommonDAO;
import com.jpower.cms.upload.common.Util;
import com.jpower.cms.vo.InventoryApplicationDetailVO;
import com.jpower.cms.vo.InventoryApplicationVO;

public class InventoryApplicationProcessor {
	
	private static String[] headerRowLabel = {"LOB", "Sub-LOB", "Sub-LOB-Label-Eng"
		, "Sub-LOB-Label-Chin", "Category", "Category-Sequence", "Category-Label-Eng"
		, "Category-Label-Chin", "Category-Image", "Action", "Series"
		, "Series-Sequence", "Series-Label-Eng", "Series-Label-Chin"
		, "Series-Image-Small", "Series-Image-Large", "Sub-Series"
		, "Sub-Series-Sequence", "Sub-Series-ID", "Sub-Series-Image-Small"
		, "Sub-Series-Image-Large"};
	
	private static void generateInventoryApplicationHeaderRow(WritableSheet ws) {
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
		vo.setSubLobLabelEng(row[columnIndex++].getContents());
		vo.setSubLobLabelChin(row[columnIndex++].getContents());
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
	
	public static int generateInventoryApplicationDataRow(WritableSheet ws, List<InventoryApplicationVO> voList) {
		int rtnCode = 0;
		int rowNum = 1;
		
		generateInventoryApplicationHeaderRow(ws);
		for (Iterator<InventoryApplicationVO> i = voList.iterator(); i.hasNext();) {
			InventoryApplicationVO vo = i.next();

			try {
				Label lbl1 = new Label(0, rowNum, vo.getLob());
				ws.addCell(lbl1);
				Label lbl2 = new Label(1, rowNum, vo.getSubLob());
				ws.addCell(lbl2);
				Label lbl3 = new Label(2, rowNum, vo.getSubLobLabelEng());
				ws.addCell(lbl3);
				Label lbl4 = new Label(3, rowNum, vo.getSubLobLabelChin());
				ws.addCell(lbl4);
				Label lbl5 = new Label(4, rowNum, vo.getCategory());
				ws.addCell(lbl5);
				Number num1 = new Number(5, rowNum, vo.getCategorySeq());
				ws.addCell(num1);
				Label lbl6 = new Label(6, rowNum, vo.getCategoryLabelEng());
				ws.addCell(lbl6);
				Label lbl7 = new Label(7, rowNum, vo.getCategoryLabelChin());
				ws.addCell(lbl7);
				Label lbl8 = new Label(8, rowNum, vo.getCategoryImagePath());
				ws.addCell(lbl8);
				Label lbl9 = new Label(9, rowNum, vo.getTranAction());
				ws.addCell(lbl9);
				Label lbl10 = new Label(10, rowNum, vo.getSeries());
				ws.addCell(lbl10);
				Number num2 = new Number(11, rowNum, vo.getSeriesSeq());
				ws.addCell(num2);				
				Label lbl11 = new Label(12, rowNum, vo.getSeriesLabelEng());
				ws.addCell(lbl11);
				Label lbl12 = new Label(13, rowNum, vo.getSeriesLabelChin());
				ws.addCell(lbl12);
				Label lbl13 = new Label(14, rowNum, vo.getSeriesImageSmallPath());
				ws.addCell(lbl13);
				Label lbl14 = new Label(15, rowNum, vo.getSeriesImageLargePath());
				ws.addCell(lbl14);
				Label lbl15 = new Label(16, rowNum, vo.getSubSeries());
				ws.addCell(lbl15);				
				Number num3 = new Number(17, rowNum, vo.getSubSeriesSeq());
				ws.addCell(num3);				
				Label lbl16 = new Label(18, rowNum, vo.getSubSeriesID());
				ws.addCell(lbl16);
				Label lbl17 = new Label(19, rowNum, vo.getSubSeriesImageSmallPath());
				ws.addCell(lbl17);
				Label lbl18 = new Label(20, rowNum, vo.getSubSeriesImageLargePath());
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
	
	public static int generateInventoryApplicationWorkbook(OutputStream out) {
		int rtnCode = 0;
		List<InventoryApplicationVO> voList = null;
		List<InventoryApplicationDetailVO> voDetailList = null;
		
		try {
//			WritableWorkbook workbook = Workbook.createWorkbook(new File("output.xls"));
			WritableWorkbook workbook = Workbook.createWorkbook(out);
			WritableSheet sheet1 = workbook.createSheet(Util.EXCEL_INVENTORY_APPLICATION, 0);
			WritableSheet sheet2 = workbook.createSheet(Util.EXCEL_INVENTORY_APPLICATION_DETAIL, 1);
			
			voList = CommonDAO.extractInventoryApplication();
			generateInventoryApplicationDataRow(sheet1, voList);
			
			voDetailList = CommonDAO.extractInventoryApplicationDetail();
			InventoryApplicationDetailProcessor.generateInventoryApplicationDetailDataRow(sheet2, voDetailList);
			
			workbook.write();
			workbook.close();
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		
		return rtnCode;
		
	}
}
