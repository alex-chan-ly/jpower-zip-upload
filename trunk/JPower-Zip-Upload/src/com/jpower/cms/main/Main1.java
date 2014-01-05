package com.jpower.cms.main;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.jpower.cms.dao.InventoryApplicationDetailVO;
import com.jpower.cms.dao.InventoryApplicationVO;
import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.DBUtil;
import com.jpower.cms.upload.common.InventoryApplicationDBProcessor;
import com.jpower.cms.upload.common.InventoryApplicationDetailDBProcessor;
import com.jpower.cms.upload.common.MemCache;
import com.jpower.cms.upload.common.Util;
import com.jpower.cms.upload.excel.InventoryApplicationDetailProcessor;
import com.jpower.cms.upload.excel.InventoryApplicationProcessor;

public class Main1 {
	
	public static void execute_3() {
		Connection conn;
		try {
			conn = DBAccess.getDBConnection();
			CallableStatement cs = conn.prepareCall("{ call CHECK_APPLICATION_UPLOAD_SUB_SERIES(?, ?)}");
			cs.setInt(1, 1328);
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.execute();
			System.out.println("CallableStatement with return : " + cs.getInt(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void execute_2() {
		Connection conn;
		try {
			conn = DBAccess.getDBConnection();
			CallableStatement cs = conn.prepareCall("{ call UPDATEDIPRECORD(?)}");
			cs.setInt(1, 13579);
			cs.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void execute_1() {
		List<InventoryApplicationVO> voList = null;
		List<InventoryApplicationDetailVO> voDetailList = null;
		int seq;
		
		File f = new File("/home/alexc/Documents/_jPower/jpower-phase-2-walkthrough-20131229.xls");
		seq = DBUtil.getUploadSequence();
		MemCache.setUploadSeq(seq);
		System.out.println("Seq : " + seq);
		
		try {
			Workbook wkb = Workbook.getWorkbook(f);
			for (int sheet = 0; sheet < wkb.getNumberOfSheets(); sheet++) {
				Sheet s = wkb.getSheet(sheet);
				System.out.println("Sheet name : " + s.getName());
				if (s.getName().equals(Util.EXCEL_INVENTORY_APPLICATION)) {
					voList = InventoryApplicationProcessor.processApplicationSheet(s);
					if (!voList.isEmpty()) {
						InventoryApplicationDBProcessor.saveToDB(voList);
					}
					
				} else if (s.getName().equals(Util.EXCEL_INVENTORY_APPLICATION_DETAIL)) {
					voDetailList = InventoryApplicationDetailProcessor.processApplicationSheet(s);
					if (!voDetailList.isEmpty()) {
						InventoryApplicationDetailDBProcessor.saveToDB(voDetailList);
					}
				}
			}

		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Main1.execute_1();
//		Main1.execute_2();
		Main1.execute_3();

	}

}
