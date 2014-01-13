package com.jpower.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.MemCache;

public class CategoryDAO {
	
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
			+ "select distinct category_pk from jpt_rlt_category_series where rec_status = 'ACT' and ref_idx= ?)";
	
	public static String sql4 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-CATEGORY', " 
			+ "'Category_pk : ' || TRIM(CAST(CAST(category_pk AS CHAR(10))AS VARCHAR(10))) || '; Category ID : ' || category_id  || ' being deleted', '', "
			+ "current_timestamp, current_timestamp from jpt_category where rec_status = 'DEL' and ref_idx = ?";

	

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
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return cnt;
	}	
}
