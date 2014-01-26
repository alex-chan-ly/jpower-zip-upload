package com.jpower.cms.upload.excel;

import java.io.File;
import java.io.IOException;
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
import com.jpower.cms.db.storedprocedures.ApplicationCategoryValidator;
import com.jpower.cms.db.storedprocedures.ApplicationDetailMaterialValidator;
import com.jpower.cms.db.storedprocedures.ApplicationSeriesValidator;
import com.jpower.cms.db.storedprocedures.ApplicationSubSeriesValidator;
import com.jpower.cms.upload.common.DBUtil;
import com.jpower.cms.upload.common.FileHelper;
import com.jpower.cms.upload.common.InventoryApplicationDBProcessor;
import com.jpower.cms.upload.common.InventoryApplicationDetailDBProcessor;
import com.jpower.cms.upload.common.MemCache;
import com.jpower.cms.upload.common.Util;
import com.jpower.cms.vo.InventoryApplicationDetailVO;
import com.jpower.cms.vo.InventoryApplicationVO;

public class ExcelUploader {
	
	private static String stagingHome = FileHelper.getConfigProperty("staging.home");
	private static String stagingDir = FileHelper.getConfigProperty("staging.directory");

	public static int preUpload() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int upload(String fileName) {
		List<InventoryApplicationVO> voList = null;
		List<InventoryApplicationDetailVO> voDetailList = null;
		int seq;
		int rtnCode = -1;
//		int updateSeq;
		
		File f = new File(fileName);
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
			rtnCode = seq;
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return rtnCode;
	}

	public static int postUpload(int refIdx) {
		
		int rtnCount = 0;
		
		rtnCount = ApplicationSubSeriesValidator.checkSubSeriesImageSmallNameWithSpaceChar(refIdx) 
				+ ApplicationSubSeriesValidator.checkSubSeriesImageLargeNameWithSpaceChar(refIdx)
				+ ApplicationSubSeriesValidator.checkSubSeriesImageSmallExists(refIdx, stagingHome, stagingDir)
				+ ApplicationSubSeriesValidator.checkSubSeriesImageLargeExists(refIdx, stagingHome, stagingDir) 
				+ ApplicationSeriesValidator.checkSeriesImageSmallNameWithSpaceChar(refIdx)
				+ ApplicationSeriesValidator.checkSeriesImageLargeNameWithSpaceChar(refIdx)
				+ ApplicationSeriesValidator.checkSeriesImageSmallExists(refIdx, stagingHome, stagingDir)
				+ ApplicationSeriesValidator.checkSeriesImageLargeExists(refIdx, stagingHome, stagingDir)
				+ ApplicationCategoryValidator.checkCategoryImageNameWithSpaceChar(refIdx)
				+ ApplicationCategoryValidator.checkCategoryImageExists(refIdx, stagingHome, stagingDir);
		
		if (rtnCount > 0) {
			return rtnCount;
		} else {
			rtnCount = ApplicationSubSeriesValidator.checkExistForAdd(refIdx) 
					+ ApplicationSubSeriesValidator.checkNotExistForDel(refIdx) 
					+ ApplicationSeriesValidator.checkNotExistForDel(refIdx) 
					+ ApplicationSeriesValidator.checkInconsistentSeriesSequence(refIdx) 
					+ ApplicationSeriesValidator.checkDuplicateSubSeriesSequence(refIdx) 
					+ ApplicationCategoryValidator.checkInconsistentCategorySequence(refIdx) 
					+ ApplicationDetailMaterialValidator.checkExistForAdd(refIdx) 
					+ ApplicationDetailMaterialValidator.checkNotExistForDel(refIdx);
		}
		
		return rtnCount;
	}

	public static int[] convertToMasterData(int refIdx) {
		
		int[] rtnCount = new int[2];
		int delCount = 0;
		int addCount = 0;
		
		delCount = MaterialDAO.deleteRecByRefIdx(refIdx) 
				+ SubSeriesDAO.deleteRecByRefIdx(refIdx) 
				+ RltSeriesSubSeriesDAO.deleteRecByRefIdx(refIdx) 
				+ SeriesDAO.deleteRecByRefIdx(refIdx) 
				+ RltCategorySeriesDAO.deleteRecByRefIdx(refIdx)
				+ CategoryDAO.deleteRecByRefIdx(refIdx) 
				+ RltLobCategoryDAO.deleteRecByRefIdx(refIdx) 
				+ LobDAO.deleteRecByRefIdx(refIdx);
		
		addCount = MaterialDAO.addRecByRefIdx(refIdx) 
				+ SubSeriesDAO.addRecByRefIdx(refIdx) 
				+ SeriesDAO.addRecByRefIdx(refIdx) 
				+ RltSeriesSubSeriesDAO.addRecByRefIdx(refIdx)
				+ CategoryDAO.addRecByRefIdx(refIdx) 
				+ RltCategorySeriesDAO.addRecByRefIdx(refIdx)
				+ LobDAO.addRecByRefIdx(refIdx) 
				+ RltLobCategoryDAO.addRecByRefIdx(refIdx);
		
		rtnCount[0] = delCount;
		rtnCount[1] = addCount;
		
		return rtnCount;
	}
	
	public static int execute(String fileName) {
		
		int refIdx = 0;
		int rtnCount = 0;
		int[] recCount = new int[2];
		
		if (preUpload() == 0) {
			refIdx = upload(fileName);
			if (refIdx != -1) {
				rtnCount = postUpload(refIdx);
				if (rtnCount == 0) {
					recCount = convertToMasterData(refIdx);
				}
			}
		}
		return 0;
	}
	
	public static int execute_A1(String fileName) {

		int refIdx = 0;
		int rtnCount = 0;
		int[] recCount = new int[2];

		rtnCount = upload(fileName);

		// if (preUpload() == 0) {
		// refIdx = upload(fileName);
		// if (refIdx != -1) {
		// rtnCount = postUpload(refIdx);
		// if (rtnCount == 0) {
		// recCount = convertToMasterData(refIdx);
		// }
		// }
		// }
		return 0;
	}
	
	public static int execute_A2() {
		
		int refIdx = 0;
		int rtnCount = 0;
		int[] recCount = new int[2];
		
//		if (preUpload() == 0) {
//			refIdx = upload(fileName);
//			if (refIdx != -1) {
				rtnCount = postUpload(3928);
				System.out.println("postUpload count : " + rtnCount);
				if (rtnCount == 0) {
					recCount = convertToMasterData(3928);
				}
//			}
//		}
		return 0;
	}
	
	public static void main(String[] args) {
		
		String stagingHome = FileHelper.getConfigProperty("staging.home");
		String stagingDir = FileHelper.getConfigProperty("staging.directory");
		String fileName = "jpower-phase-2-19012014-Amend-unitTest.xls";
		
		String fullPath = stagingHome + File.separator + stagingDir + File.separator + fileName; 
		
//		int cnt = execute_A1(fullPath);
		int cnt = execute_A2();
	}
}
