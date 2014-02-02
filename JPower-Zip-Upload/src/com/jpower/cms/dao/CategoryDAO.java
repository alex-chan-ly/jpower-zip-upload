package com.jpower.cms.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.FileHelper;
import com.jpower.cms.upload.common.MemCache;

public class CategoryDAO {

	private static String storageHome = FileHelper.getConfigProperty("storage.home");
	private static String contentHome = FileHelper.getConfigProperty("content.home");
	private static String stagingDirectory = FileHelper.getConfigProperty("staging.directory");
	private static String storageDirectory = FileHelper.getConfigProperty("storage.directory");
	
	
	public static String sql1 = "insert into jpt_category (ref_idx, category_id, category_label_eng, category_label_chin, category_image, rec_status, create_date, "
			+ "update_date, create_user, update_user) "
			+ "select distinct ref_idx, app.category, app.category_label_eng, app.category_label_chin, app.category_image, 'ACT', "
			+ "current_timestamp, current_timestamp, 'john', 'john' from jpw_application app where app.tran_action = 'ADD' and app.tran_status = 'AWV' and app.ref_idx = ?"
			+ "and not exists (select * from jpt_category c where c.category_id = app.category and c.rec_status = 'ACT')";
	
	public static String sql2 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'ADDITION-CATEGORY', " 
			+ "'Category_pk : ' || TRIM(CAST(CAST(category_pk AS CHAR(10))AS VARCHAR(10))) || '; Category_ID : ' || category_id  || ' being added', '', "
			+ "current_timestamp, current_timestamp from jpt_category where rec_status = 'ACT' and ref_idx = ?";
	
	public static String sql3 = "update jpt_category set ref_idx = ?, rec_status = 'DEL', update_date = current_timestamp where rec_status = 'ACT' and category_pk in ("
			+ "select distinct category_pk from jpt_rlt_category_series where rec_status = 'DEL' and ref_idx= ? except "
			+ "select distinct category_pk from jpt_rlt_category_series where rec_status = 'ACT' and category_pk in ("
			+ "select distinct category_pk from jpt_rlt_category_series where rec_status = 'DEL' and ref_idx= ?))";
	
	public static String sql4 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-CATEGORY', " 
			+ "'Category_pk : ' || TRIM(CAST(CAST(category_pk AS CHAR(10))AS VARCHAR(10))) || '; Category ID : ' || category_id  || ' being deleted', '', "
			+ "current_timestamp, current_timestamp from jpt_category where rec_status = 'DEL' and ref_idx = ?";

	public static String sql5 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-CATEGORY_IMAGE_FILE', "
			+ "purge_file(?, (case when upper(lob.sub_lob_id) = 'COMMERCIAL' then 'commercial/1' else (case when upper(lob.sub_lob_id) = 'RESIDENTIAL' then 'residential/1' end) end), "
			+ "cat.category_image), 'Image file being purged', " 
			+ "current_timestamp, current_timestamp from jpt_category cat, jpt_rlt_lob_category lob_cat, jpt_lob lob where cat.rec_status = 'DEL' and cat.ref_idx = ? "
			+ "and cat.category_pk = lob_cat.category_pk and lob_cat.lob_pk = lob.lob_pk";
	
	public static String sql6 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'ADDITION-CATEGORY_IMAGE_FILE', "
			+ "copy_file(?, ?, cat.category_image, ? || (case when upper(lob.sub_lob_id) = 'COMMERCIAL' then 'commercial/1' else (case when upper(lob.sub_lob_id) = 'RESIDENTIAL' then 'residential/1' end) end), cat.category_image), "
			+ "'Image file being added', current_timestamp, current_timestamp from jpt_category cat, jpt_rlt_lob_category lob_cat, "
			+ "jpt_lob lob where cat.rec_status = 'ACT' and cat.ref_idx = ? and cat.category_pk = lob_cat.category_pk and lob_cat.lob_pk = lob.lob_pk";

	public static int deleteRecByRefIdx(int refIdx) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		
		conn = DBAccess.getDBConnection();
		try {
			ps = conn.prepareStatement(sql3);
			ps.setInt(1, refIdx);
			ps.setInt(2, refIdx);
			ps.setInt(3, refIdx);
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
	
	public static int addRecByRefIdx(int refIdx) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		
		conn = DBAccess.getDBConnection();
		try {
			ps = conn.prepareStatement(sql1);
			ps.setInt(1, refIdx);
			cnt = ps.executeUpdate();
			
			if (cnt > 0) {
				System.out.println(sql2);
				ps1 = conn.prepareStatement(sql2);
//				ps1.setInt(1, MemCache.getChangeLogKey());
				ps1.setInt(1, refIdx);
				ps1.setInt(2, refIdx);
				cnt = ps1.executeUpdate();
			}
			
			if (cnt > 0) {
				RltSeriesSubSeriesDAO.addRecByRefIdx(refIdx);
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
	
	public static int purgeImageFileByRefIdx(int refIdx) {
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
	
	public static int copyImageFileToStorageContentByRefIdx(int refIdx) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		
		conn = DBAccess.getDBConnection();
		try {
			ps = conn.prepareStatement(sql6);
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
