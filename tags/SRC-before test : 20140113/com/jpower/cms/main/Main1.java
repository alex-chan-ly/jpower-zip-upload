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

import com.jpower.cms.dao.CategoryDAO;
import com.jpower.cms.dao.LobDAO;
import com.jpower.cms.dao.MaterialDAO;
import com.jpower.cms.dao.RltCategorySeriesDAO;
import com.jpower.cms.dao.RltLobCategoryDAO;
import com.jpower.cms.dao.RltSeriesSubSeriesDAO;
import com.jpower.cms.dao.SeriesDAO;
import com.jpower.cms.dao.SubSeriesDAO;
import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.DBUtil;
import com.jpower.cms.upload.common.InventoryApplicationDBProcessor;
import com.jpower.cms.upload.common.InventoryApplicationDetailDBProcessor;
import com.jpower.cms.upload.common.MemCache;
import com.jpower.cms.upload.common.Util;
import com.jpower.cms.upload.excel.InventoryApplicationDetailProcessor;
import com.jpower.cms.upload.excel.InventoryApplicationProcessor;
import com.jpower.cms.vo.InventoryApplicationDetailVO;
import com.jpower.cms.vo.InventoryApplicationVO;

public class Main1 {
	
	public static void execute_4() {
		MaterialDAO.deleteRecByRefIdx(1328);
	}
	
	public static void execute_5() {
		SeriesDAO.addRecByRefIdx(1328);
	}
	
	
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
		int seq, updateSeq;
		
		File f = new File("/home/alexc/Documents/_jPower/jpower-phase-2-walkthrough-20131229.xls");
		seq = DBUtil.getUploadSequence();
		MemCache.setUploadSeq(seq);
		updateSeq = DBUtil.getUploadSequence();
		MemCache.setChangeLogKey(updateSeq);
		
		System.out.println("Seq : " + seq);
		System.out.println("ChangeLogKey : " + updateSeq);
		
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

	public static void execute_A1() {
		List<InventoryApplicationVO> voList = null;
		List<InventoryApplicationDetailVO> voDetailList = null;
		int seq, updateSeq;
		
		File f = new File("/home/alexc/Documents/_jPower/jpower-phase-2-walkthrough-20131229.xls");
		seq = DBUtil.getUploadSequence();
		MemCache.setUploadSeq(seq);
		updateSeq = DBUtil.getUploadSequence();
		MemCache.setChangeLogKey(updateSeq);
		
		System.out.println("Seq : " + seq);
		System.out.println("ChangeLogKey : " + updateSeq);
		
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
	
	public static void execute_MAY1() {
		List<InventoryApplicationVO> voList = null;
		List<InventoryApplicationDetailVO> voDetailList = null;
		int seq, updateSeq;
		
		File f = new File("/home/alexc/Documents/_jPower/jpower-phase-2-13012014-May-Amend.xls");
		seq = DBUtil.getUploadSequence();
		MemCache.setUploadSeq(seq);
		updateSeq = DBUtil.getUploadSequence();
		MemCache.setChangeLogKey(updateSeq);
		
		System.out.println("Seq : " + seq);
		System.out.println("ChangeLogKey : " + updateSeq);
		
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
	
	public static void execute_A2() {
		MaterialDAO.deleteRecByRefIdx(2128);
		SubSeriesDAO.deleteRecByRefIdx(2128);
		RltSeriesSubSeriesDAO.deleteRecByRefIdx(2128);
		SeriesDAO.deleteRecByRefIdx(2128);
		RltCategorySeriesDAO.deleteRecByRefIdx(2128);
		CategoryDAO.deleteRecByRefIdx(2128);
		RltLobCategoryDAO.deleteRecByRefIdx(2128);
		LobDAO.deleteRecByRefIdx(2128);
	}
	
	public static void execute_A3() {
		MaterialDAO.addRecByRefIdx(2728);
		SubSeriesDAO.addRecByRefIdx(2728);
		SeriesDAO.addRecByRefIdx(2728);
		RltSeriesSubSeriesDAO.addRecByRefIdx(2728);
	}
	
	public static void execute_A4() {
		CategoryDAO.addRecByRefIdx(2728);
		RltCategorySeriesDAO.addRecByRefIdx(2728);
		LobDAO.addRecByRefIdx(2728);
		RltLobCategoryDAO.addRecByRefIdx(2728);
	}	
	public static void execute_MAY3() {
		MaterialDAO.addRecByRefIdx(3428);
		SubSeriesDAO.addRecByRefIdx(3428);
		SeriesDAO.addRecByRefIdx(3428);
		RltSeriesSubSeriesDAO.addRecByRefIdx(3428);
	}
	
	public static void execute_MAY4() {
		CategoryDAO.addRecByRefIdx(3428);
		RltCategorySeriesDAO.addRecByRefIdx(3428);
		LobDAO.addRecByRefIdx(3428);
		RltLobCategoryDAO.addRecByRefIdx(3428);
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Main1.execute_1();
//		Main1.execute_2();
//		Main1.execute_3();
//		Main1.execute_4();
//		Main1.execute_5();
//		Main1.execute_A1();
//		Main1.execute_A2();
//		Main1.execute_MAY1();
		Main1.execute_MAY3();
		Main1.execute_MAY4();
//		

	}

}
