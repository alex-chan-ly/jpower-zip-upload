package com.jpower.cms.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.FileHelper;
import com.jpower.cms.upload.common.MemCache;

public class SubSeriesDAO {

	private static String storageHome = FileHelper.getConfigProperty("storage.home");
	private static String contentHome = FileHelper.getConfigProperty("content.home");
	private static String stagingDirectory = FileHelper.getConfigProperty("staging.directory");
	private static String storageDirectory = FileHelper.getConfigProperty("storage.directory");
	
	public static String sql1 = "update jpt_sub_series set ref_idx = ?, rec_status = 'DEL', update_date = current_timestamp where rec_status = 'ACT' and material_id in ("
			+ "select sub_series_id from jpw_application where tran_action = 'DEL' and ref_idx = ?)";
	
	public static String sql2 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-SUB_SERIES', "
			+ "'Sub Series ID : ' || sub_series_id || '; Material_ID : ' || material_id || ' being marked deletion', '', current_timestamp, current_timestamp from jpt_sub_series where rec_status = 'DEL' and ref_idx = ?";	
			
	public static String sql3 = "insert into jpt_sub_series (ref_idx, sub_series_id, material_id, sub_series_image_small, sub_series_image_large, "
		+ "rec_status, create_date, update_date, create_user, update_user) "
		+ "select app.ref_idx, app.sub_series, app.sub_series_id, app.sub_series_image_small, app.sub_series_image_large, 'ACT', "
		+ "current_timestamp, current_timestamp, 'john', 'john' from jpw_application app where app.tran_action = 'ADD' and app.tran_status = 'AWV' and app.ref_idx = ? "
		+ "and not exists (select * from jpt_sub_series ss where ss.material_id = app.sub_series_id and ss.rec_status = 'ACT')";
	
	public static String sql4 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) " 
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'ADDITION-SUB_SERIES', "
			+ "'Sub Series / Sub Series ID : ' || sub_series || ' / ' || sub_series_id || ' being added', '', "
			+ "current_timestamp, current_timestamp from jpw_application where tran_action = 'ADD' and tran_status = 'AWV' and ref_idx = ?";
	
	public static String sql5 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-SUB_SERIES_SMALL_IMAGE_FILE', "
			+ "purge_file(?, (case when upper(lob.sub_lob_id) = 'COMMERCIAL' then 'commercial/3' else (case when upper(lob.sub_lob_id) = 'RESIDENTIAL' then 'residential/3' end) end), "
			+ "sub_ser.sub_series_image_small), 'Small image file being purged', " 
			+ "current_timestamp, current_timestamp from jpt_sub_series sub_ser, jpt_rlt_series_sub_series rlt_ser_sub_ser, jpt_series ser, "
			+ "jpt_rlt_category_series cat_ser, jpt_category cat, jpt_rlt_lob_category lob_cat, jpt_lob lob "
			+ "where sub_ser.rec_status = 'DEL' and sub_ser.ref_idx = ? and sub_ser.sub_series_pk = rlt_ser_sub_ser.sub_series_pk "
			+ "and rlt_ser_sub_ser.series_pk = ser.series_pk and ser.series_pk = cat_ser.series_pk and "
			+ "cat_ser.category_pk = cat.category_pk and cat.category_pk = lob_cat.category_pk and lob_cat.lob_pk = lob.lob_pk";	
	
	public static String sql6 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-SUB_SERIES_LARGE_IMAGE_FILE', "
			+ "purge_file(?, (case when upper(lob.sub_lob_id) = 'COMMERCIAL' then 'commercial/3' else (case when upper(lob.sub_lob_id) = 'RESIDENTIAL' then 'residential/3' end) end), "
			+ "sub_ser.sub_series_image_large), 'Large image file being purged', " 
			+ "current_timestamp, current_timestamp from jpt_sub_series sub_ser, jpt_rlt_series_sub_series rlt_ser_sub_ser, jpt_series ser, "
			+ "jpt_rlt_category_series cat_ser, jpt_category cat, jpt_rlt_lob_category lob_cat, jpt_lob lob "
			+ "where sub_ser.rec_status = 'DEL' and sub_ser.ref_idx = ? and sub_ser.sub_series_pk = rlt_ser_sub_ser.sub_series_pk "
			+ "and rlt_ser_sub_ser.series_pk = ser.series_pk and ser.series_pk = cat_ser.series_pk and "
			+ "cat_ser.category_pk = cat.category_pk and cat.category_pk = lob_cat.category_pk and lob_cat.lob_pk = lob.lob_pk";
	
	public static String sql7 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'ADDITION-SUB_SERIES_SMALL_IMAGE_FILE', "
			+ "copy_file(?, ?, sub_ser.sub_series_image_small, ? || (case when upper(lob.sub_lob_id) = 'COMMERCIAL' then 'commercial/3' else (case when upper(lob.sub_lob_id) = 'RESIDENTIAL' then 'residential/3' end) end), sub_ser.sub_series_image_small), "
			+ "'Small image file being added', " 
			+ "current_timestamp, current_timestamp from jpt_sub_series sub_ser, jpt_rlt_series_sub_series rlt_ser_sub_ser, jpt_series ser, "
			+ "jpt_rlt_category_series cat_ser, jpt_category cat, jpt_rlt_lob_category lob_cat, jpt_lob lob "
			+ "where sub_ser.rec_status = 'ACT' and sub_ser.ref_idx = ? and sub_ser.sub_series_pk = rlt_ser_sub_ser.sub_series_pk "
			+ "and rlt_ser_sub_ser.series_pk = ser.series_pk and ser.series_pk = cat_ser.series_pk and "
			+ "cat_ser.category_pk = cat.category_pk and cat.category_pk = lob_cat.category_pk and lob_cat.lob_pk = lob.lob_pk";
	
	public static String sql8 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'ADDITION-SUB_SERIES_LARGE_IMAGE_FILE', "
			+ "copy_file(?, ?, sub_ser.sub_series_image_large, ? || (case when upper(lob.sub_lob_id) = 'COMMERCIAL' then 'commercial/3' else (case when upper(lob.sub_lob_id) = 'RESIDENTIAL' then 'residential/3' end) end), sub_ser.sub_series_image_large), "
			+ "'Large image file being added', " 
			+ "current_timestamp, current_timestamp from jpt_sub_series sub_ser, jpt_rlt_series_sub_series rlt_ser_sub_ser, jpt_series ser, "
			+ "jpt_rlt_category_series cat_ser, jpt_category cat, jpt_rlt_lob_category lob_cat, jpt_lob lob "
			+ "where sub_ser.rec_status = 'ACT' and sub_ser.ref_idx = ? and sub_ser.sub_series_pk = rlt_ser_sub_ser.sub_series_pk "
			+ "and rlt_ser_sub_ser.series_pk = ser.series_pk and ser.series_pk = cat_ser.series_pk and "
			+ "cat_ser.category_pk = cat.category_pk and cat.category_pk = lob_cat.category_pk and lob_cat.lob_pk = lob.lob_pk";	
	
	public static int deleteRecByRefIdx(int refIdx) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		
		conn = DBAccess.getDBConnection();
		try {
			ps = conn.prepareStatement(sql1);
			ps.setInt(1, refIdx);
			ps.setInt(2, refIdx);
			cnt = ps.executeUpdate();
			
			if (cnt > 0) {
				ps1 = conn.prepareStatement(sql2);
//				ps1.setInt(1, MemCache.getChangeLogKey());
				ps1.setInt(1, refIdx);
				ps1.setInt(2, refIdx);
				ps1.executeUpdate();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (ps != null) {ps.close();}
				if (ps1 != null) {ps1.close();}
				if (conn != null) {conn.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return cnt;
	}
	
	public static int addRecByRefIdx(int refIdx) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		
		conn = DBAccess.getDBConnection();
		try {
			ps = conn.prepareStatement(sql3);
			ps.setInt(1, refIdx);
			cnt = ps.executeUpdate();
			
			if (cnt > 0) {
				ps1 = conn.prepareStatement(sql4);
//				ps1.setInt(1, MemCache.getChangeLogKey());
				ps1.setInt(1, refIdx);
				ps1.setInt(2, refIdx);
				ps1.executeUpdate();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (ps != null) {ps.close();}
				if (ps1 != null) {ps1.close();}
				if (conn != null) {conn.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return cnt;
	}	
	
	public static int purgeImageSmallFileByRefIdx(int refIdx) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement ps = null;
	
		conn = DBAccess.getDBConnection();
		try {
			ps = conn.prepareStatement(sql5);
			ps.setInt(1, refIdx);
			ps.setString(2, storageHome);
			ps.setInt(3, refIdx);
			cnt = ps.executeUpdate();
	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (ps != null) {ps.close();}
				if (conn != null) {conn.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		return cnt;
	}

	public static int purgeImageLargeFileByRefIdx(int refIdx) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement ps = null;
	
		conn = DBAccess.getDBConnection();
		try {
			ps = conn.prepareStatement(sql6);
			ps.setInt(1, refIdx);
			ps.setString(2, storageHome);
			ps.setInt(3, refIdx);
			cnt = ps.executeUpdate();
	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (ps != null) {ps.close();}
				if (conn != null) {conn.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		return cnt;
	}
	
	public static int copySmallImageFileToStorageContentByRefIdx(int refIdx) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		
		conn = DBAccess.getDBConnection();
		try {
			ps = conn.prepareStatement(sql7);
			ps.setInt(1, refIdx);
			ps.setString(2, contentHome);
			ps.setString(3, stagingDirectory);
			ps.setString(4, storageDirectory + File.separator);
			ps.setInt(5, refIdx);
			cnt = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (ps != null) {ps.close();}
				if (conn != null) {conn.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return cnt;
	}
	
	public static int copyLargeImageFileToStorageContentByRefIdx(int refIdx) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		
		conn = DBAccess.getDBConnection();
		try {
			ps = conn.prepareStatement(sql8);
			ps.setInt(1, refIdx);
			ps.setString(2, contentHome);
			ps.setString(3, stagingDirectory);
			ps.setString(4, storageDirectory + File.separator);
			ps.setInt(5, refIdx);
			cnt = ps.executeUpdate();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (ps != null) {ps.close();}
				if (conn != null) {conn.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		return cnt;
	}			
}
