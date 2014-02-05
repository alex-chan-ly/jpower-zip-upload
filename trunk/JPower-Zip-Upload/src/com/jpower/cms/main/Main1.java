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
import com.jpower.cms.db.storedprocedures.ApplicationSubSeriesValidator;
import com.jpower.cms.db.storedprocedures.StoredUtil;
import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.DBUtil;
import com.jpower.cms.upload.common.InventoryApplicationDBProcessor;
import com.jpower.cms.upload.common.InventoryApplicationDetailDBProcessor;
import com.jpower.cms.upload.common.MemCache;
import com.jpower.cms.upload.common.StagingUtil;
import com.jpower.cms.upload.common.Util;
import com.jpower.cms.upload.excel.InventoryApplicationDetailProcessor;
import com.jpower.cms.upload.excel.InventoryApplicationProcessor;
import com.jpower.cms.upload.excel.LogProcessor;
import com.jpower.cms.vo.InventoryApplicationDetailVO;
import com.jpower.cms.vo.InventoryApplicationVO;

public class Main1 {
	
	public static void execute_stagUtil_A1() {
		StagingUtil.purgeStagingArea();
	}
	
	public static void execute_jPowerBkup_A1() {
		int recCont = ApplicationSubSeriesValidator.checkSubSeriesImageLargeExists(3428, 
				"/home/alexc/workspace_ee/JPower-Zip-Upload/public_html", "staging");
		System.out.println("execute_jPowerBkup_A1 : " + recCont);
	}
	
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
		int seq;
//		int updateSeq;
		
//		File f = new File("/home/alexc/Documents/_jPower/jpower-phase-2-walkthrough-20131229.xls");
//		File f = new File("/home/alexc/Documents/_jPower/jpower-phase-2-walkthrough-20131229-working.xls");
		File f = new File("/home/alexc/Documents/_jPower/_inventory/website 26012014/jpower-phase-2-19012014-Amend.xls");
		seq = DBUtil.getUploadSequence();
//		MemCache.setUploadSeq(seq);
//		updateSeq = DBUtil.getUploadSequence();
//		MemCache.setChangeLogKey(updateSeq);
		
		System.out.println("Seq : " + seq);
//		System.out.println("ChangeLogKey : " + updateSeq);
		
		try {
			Workbook wkb = Workbook.getWorkbook(f);
			for (int sheet = 0; sheet < wkb.getNumberOfSheets(); sheet++) {
				Sheet s = wkb.getSheet(sheet);
				System.out.println("Sheet name : " + s.getName());
				if (s.getName().equals(Util.EXCEL_INVENTORY_APPLICATION)) {
					voList = InventoryApplicationProcessor.processApplicationSheet(s);
					if (!voList.isEmpty()) {
						InventoryApplicationDBProcessor.saveToDB(voList, seq);
					}
					
				} else if (s.getName().equals(Util.EXCEL_INVENTORY_APPLICATION_DETAIL)) {
					voDetailList = InventoryApplicationDetailProcessor.processApplicationSheet(s);
					if (!voDetailList.isEmpty()) {
						InventoryApplicationDetailDBProcessor.saveToDB(voDetailList, seq);
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
		int seq;
//		int updateSeq;
		
		File f = new File("/home/alexc/Documents/_jPower/jpower-phase-2-walkthrough-20131229.xls");
		seq = DBUtil.getUploadSequence();
//		MemCache.setUploadSeq(seq);
//		updateSeq = DBUtil.getUploadSequence();
//		MemCache.setChangeLogKey(updateSeq);
		
		System.out.println("Seq : " + seq);
//		System.out.println("ChangeLogKey : " + updateSeq);
		
		try {
			Workbook wkb = Workbook.getWorkbook(f);
			for (int sheet = 0; sheet < wkb.getNumberOfSheets(); sheet++) {
				Sheet s = wkb.getSheet(sheet);
				System.out.println("Sheet name : " + s.getName());
				if (s.getName().equals(Util.EXCEL_INVENTORY_APPLICATION)) {
					voList = InventoryApplicationProcessor.processApplicationSheet(s);
					if (!voList.isEmpty()) {
						InventoryApplicationDBProcessor.saveToDB(voList, seq);
					}
					
				} else if (s.getName().equals(Util.EXCEL_INVENTORY_APPLICATION_DETAIL)) {
					voDetailList = InventoryApplicationDetailProcessor.processApplicationSheet(s);
					if (!voDetailList.isEmpty()) {
						InventoryApplicationDetailDBProcessor.saveToDB(voDetailList, seq);
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
		int seq;
//		int updateSeq;
		
		File f = new File("/home/alexc/Documents/_jPower/jpower-phase-2-14012014-May-Amend-20140118.xls");
		seq = DBUtil.getUploadSequence();
//		MemCache.setUploadSeq(seq);
//		updateSeq = DBUtil.getUploadSequence();
//		MemCache.setChangeLogKey(updateSeq);
		
		System.out.println("Seq : " + seq);
//		System.out.println("ChangeLogKey : " + updateSeq);
		
		try {
			Workbook wkb = Workbook.getWorkbook(f);
			for (int sheet = 0; sheet < wkb.getNumberOfSheets(); sheet++) {
				Sheet s = wkb.getSheet(sheet);
				System.out.println("Sheet name : " + s.getName());
				if (s.getName().equals(Util.EXCEL_INVENTORY_APPLICATION)) {
					voList = InventoryApplicationProcessor.processApplicationSheet(s);
					if (!voList.isEmpty()) {
						InventoryApplicationDBProcessor.saveToDB(voList, seq);
					}
					
				} else if (s.getName().equals(Util.EXCEL_INVENTORY_APPLICATION_DETAIL)) {
					voDetailList = InventoryApplicationDetailProcessor.processApplicationSheet(s);
					if (!voDetailList.isEmpty()) {
						InventoryApplicationDetailDBProcessor.saveToDB(voDetailList, seq);
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
	
	public static void execute_inventory1() {
		List<InventoryApplicationVO> voList = null;
		List<InventoryApplicationDetailVO> voDetailList = null;
		int seq;
//		int updateSeq;
		
//		File f = new File("/home/alexc/Documents/_jPower/jpower-phase-2-inventory-1.xls");
		File f = new File("/home/alexc/workspace_ee/JPower-Web/JPower/content/staging/jpower-phase-2-19012014.xls");
		seq = DBUtil.getUploadSequence();
//		MemCache.setUploadSeq(seq);
//		updateSeq = DBUtil.getUploadSequence();
//		MemCache.setChangeLogKey(updateSeq);
		
		System.out.println("Seq : " + seq);
//		System.out.println("ChangeLogKey : " + updateSeq);
		
		try {
			Workbook wkb = Workbook.getWorkbook(f);
			for (int sheet = 0; sheet < wkb.getNumberOfSheets(); sheet++) {
				Sheet s = wkb.getSheet(sheet);
				System.out.println("Sheet name : " + s.getName());
				if (s.getName().equals(Util.EXCEL_INVENTORY_APPLICATION)) {
					voList = InventoryApplicationProcessor.processApplicationSheet(s);
					if (!voList.isEmpty()) {
						InventoryApplicationDBProcessor.saveToDB(voList, seq);
					}
					
				} else if (s.getName().equals(Util.EXCEL_INVENTORY_APPLICATION_DETAIL)) {
					voDetailList = InventoryApplicationDetailProcessor.processApplicationSheet(s);
					if (!voDetailList.isEmpty()) {
						InventoryApplicationDetailDBProcessor.saveToDB(voDetailList, seq);
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
		MaterialDAO.deleteRecByRefIdx(4028);
		SubSeriesDAO.deleteRecByRefIdx(4028);
		RltSeriesSubSeriesDAO.deleteRecByRefIdx(4028);
		SeriesDAO.deleteRecByRefIdx(4028);
		RltCategorySeriesDAO.deleteRecByRefIdx(4028);
		CategoryDAO.deleteRecByRefIdx(4028);
		RltLobCategoryDAO.deleteRecByRefIdx(4028);
		LobDAO.deleteRecByRefIdx(4028);
	}
	
	public static void execute_A3() {
		MaterialDAO.addRecByRefIdx(3528);
		SubSeriesDAO.addRecByRefIdx(3528);
		SeriesDAO.addRecByRefIdx(3528);
		RltSeriesSubSeriesDAO.addRecByRefIdx(3528);
	}
	
	public static void execute_A4() {
		CategoryDAO.addRecByRefIdx(3528);
		RltCategorySeriesDAO.addRecByRefIdx(3528);
		LobDAO.addRecByRefIdx(3528);
		RltLobCategoryDAO.addRecByRefIdx(3528);
	}	
	public static void execute_MAY3() {
		MaterialDAO.addRecByRefIdx(4228);
		SubSeriesDAO.addRecByRefIdx(4228);
		SeriesDAO.addRecByRefIdx(4228);
		RltSeriesSubSeriesDAO.addRecByRefIdx(4228);
	}
	
	public static void execute_MAY4() {
		CategoryDAO.addRecByRefIdx(4228);
		RltCategorySeriesDAO.addRecByRefIdx(4228);
		LobDAO.addRecByRefIdx(4228);
		RltLobCategoryDAO.addRecByRefIdx(4228);
	}	
	
	public static void execute_100() {
		MaterialDAO.addRecByRefIdx(4028);
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
//		Main1.execute_MAY3();
//		Main1.execute_MAY4();

//     for verify addition and deletion		
//		execute_inventory1();
//		execute_A2();
		
//		execute_A3();
//		execute_A4();
//		CategoryDAO.purgeImageFileByRefIdx(3428);
//		SeriesDAO.purgeImageSmallFileByRefIdx(3428);
//		SeriesDAO.purgeImageLargeFileByRefIdx(3428);
//		SubSeriesDAO.purgeImageLargeFileByRefIdx(3428);
//		SubSeriesDAO.purgeImageSmallFileByRefIdx(3428);
		
//		Main1.execute_1();
//		execute_100();
		
//		execute_jPowerBkup_A1();
//		execute_stagUtil_A1();
		
//		System.out.println("result : " + StoredUtil.copyFile("/tmp", "a1", "a1fs.txt", "a2", "a100.txt"));
//		CategoryDAO.copyImageFileToStorageContentByRefIdx(3428);
//		SeriesDAO.copySmallImageFileToStorageContentByRefIdx(3428);
//		SeriesDAO.copyLargeImageFileToStorageContentByRefIdx(3428);
		
//		SubSeriesDAO.copySmallImageFileToStorageContentByRefIdx(3428);
//		SubSeriesDAO.copyLargeImageFileToStorageContentByRefIdx(3428);
		
//		InventoryApplicationProcessor.generateInventoryApplicationWorkbook();
//		LogProcessor.generateLogDetailWorkbook(3428);
		execute_inventory1();				
		
	}

}
