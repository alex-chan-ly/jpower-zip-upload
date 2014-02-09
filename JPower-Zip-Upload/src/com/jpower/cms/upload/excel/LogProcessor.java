package com.jpower.cms.upload.excel;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.jpower.cms.dao.LogDAO;
import com.jpower.cms.vo.LogVO;

public class LogProcessor {
	
	private static String[] headerRowLabel = {"Ref-No", "Severity", "Category"
		, "Log-Message", "Remarks", "Create-Date"};
	
	private static void generateLogDetailHeaderRow(WritableSheet ws) {
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


	public static int generateLogDetailDataRow(WritableSheet ws, List<LogVO> voList) {
		int rtnCode = 0;
		int rowNum = 1;
		
		DateFormat customDateFormat = new DateFormat ("dd-MMM-yyyy hh:mm:ss");
		WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat);
		
		for (Iterator<LogVO> i = voList.iterator(); i.hasNext();) {
			LogVO vo = i.next();

			try {
				Label lbl1 = new Label(0, rowNum, vo.getRefIdx());
				ws.addCell(lbl1);
				Label lbl2 = new Label(1, rowNum, vo.getSeverity());
				ws.addCell(lbl2);
				Label lbl3 = new Label(2, rowNum, vo.getCategory());
				ws.addCell(lbl3);
				Label lbl4 = new Label(3, rowNum, vo.getLogMessage());
				ws.addCell(lbl4);
				Label lbl5 = new Label(4, rowNum, vo.getRemarks1());
				ws.addCell(lbl5);
				
				DateTime dateCell = new DateTime(5, rowNum, vo.getCreateDate(), dateFormat); 
				ws.addCell(dateCell);
								
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
	
	public static int generateLogDetailWorkbook(int refIdx, OutputStream out) {
		int rtnCode = 0;
		List<LogVO> voList = null;
		
		try {
//			WritableWorkbook workbook = Workbook.createWorkbook(new File("output-log.xls"));
			WritableWorkbook workbook = Workbook.createWorkbook(out);
			WritableSheet sheet1 = workbook.createSheet("log-info", 0);
			
			generateLogDetailHeaderRow(sheet1);
			
			voList = LogDAO.extractLogDetailByRefIdx(refIdx);
			
			generateLogDetailDataRow(sheet1, voList);

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
