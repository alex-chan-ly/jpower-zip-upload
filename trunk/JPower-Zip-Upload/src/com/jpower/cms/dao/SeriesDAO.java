package com.jpower.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.MemCache;

public class SeriesDAO {
	
	public static String sql1 = "insert into jpt_series (ref_idx, series_id, series_label_eng, series_label_chin, series_image_small, series_image_large, "
			+ "rec_status, create_date, update_date, create_user, update_user) "
			+ "select distinct ref_idx, app.series, app.series_label_eng, app.series_label_chin, app.series_image_small, app.series_image_large, 'ACT', "
			+ "current_timestamp, current_timestamp, 'john', 'john' from jpw_application app where app.tran_action = 'ADD' and app.tran_status = 'AWV' and app.ref_idx = ? "
			+ "and not exists (select * from jpt_series s where s.series_id = app.series and s.rec_status = 'ACT')";
	
	public static String sql2 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'ADDITION-SERIES', " 
			+ "'Series_pk : ' || TRIM(CAST(CAST(series_pk AS CHAR(10))AS VARCHAR(10))) || '; Series ID : ' || series_id  || ' being added', '', "
			+ "current_timestamp, current_timestamp from jpt_series where rec_status = 'ACT' and ref_idx = ?";
	
	public static String sql3 = "update jpt_series set ref_idx = ?, rec_status = 'DEL', update_date = current_timestamp where series_pk in ("
			+ "select distinct series_pk from jpt_rlt_series_sub_series where rec_status = 'DEL' and ref_idx= ? except "
			+ "select distinct series_pk from jpt_rlt_series_sub_series where rec_status = 'ACT' and ref_idx= ?)";
	
	public static String sql4 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-SERIES', " 
			+ "'Series_pk : ' || TRIM(CAST(CAST(series_pk AS CHAR(10))AS VARCHAR(10))) || '; Series ID : ' || series_id  || ' being deleted', '', "
			+ "current_timestamp, current_timestamp from jpt_series where rec_status = 'DEL' and ref_idx = ?";

	

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
