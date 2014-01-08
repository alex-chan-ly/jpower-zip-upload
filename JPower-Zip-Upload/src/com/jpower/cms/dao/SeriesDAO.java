package com.jpower.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.MemCache;

public class SeriesDAO {
	
	public static String sql1 = "insert into jpt_series (series_id, series_label_eng, series_label_chin, series_image_small, series_image_large, "
			+ "rec_status, create_date, update_date, create_user, update_user) "
			+ "select distinct series, series_label_eng, series_label_chin, series_image_small, series_image_large, 'ACT', "
			+ "current_timestamp, current_timestamp, 'john', 'john' from jpw_application where tran_action = 'ADD' and tran_status = 'AWV' and ref_idx = ?";
	
	public static String sql1a = "insert into jpt_rlt_series_sub_series (series_pk, sub_series_pk, series_sub_series_seq, rec_status, create_date, update_date, create_user, update_user) "
			+ "select master_s.series_pk, master_sub_s.sub_series_pk, drv.sub_series_seq, 'ACT', current_timestamp, current_timestamp, 'john', 'john' from "
			+ "(select distinct series, sub_series_id, sub_series_seq from jpw_application where tran_action = 'ADD' and tran_status = 'AWV' and ref_idx = ?) drv,"
			+ "(select series_pk, series_id from jpt_series where rec_status = 'ACT') master_s,"
			+ "(select sub_series_pk, material_id from jpt_sub_series where rec_status = 'ACT') master_sub_s "
			+ "where drv.series = master_s.series_id and drv.sub_series_id = master_sub_s.material_id";
	
	public static String sql2 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'ADDITION-SERIES', "
			+ "'Series : ' || series || ' being added', '', "
			+ "current_timestamp, current_timestamp from jpw_application where tran_action = 'ADD' and tran_status = 'AWV' and ref_idx = ?";
		
	
	
	public static String sql3 = "update jpt_sub_series set rec_status = 'DEL', update_date = current_timestamp where rec_status = 'ACT' and sub_series_id in ("
			+ "select sub_series_id from jpw_application where tran_action = 'DEL' and ref_idx = ?)";
	
	public static String sql4 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-APPLICATION', "
			+ "'Sub Series ID : ' || sub_series_id || ' being marked deletion', '', current_timestamp, current_timestamp from jpw_application where tran_action = 'DEL' and ref_idx = ?";
	

	public static int deleteRecByRefIdx(int refIdx) {
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
				ps1.setInt(1, MemCache.getChangeLogKey());
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
		PreparedStatement psA = null;
		PreparedStatement ps1 = null;
		
		conn = DBAccess.getDBConnection();
		try {
			ps = conn.prepareStatement(sql1);
			ps.setInt(1, refIdx);
			cnt = ps.executeUpdate();
			
			if (cnt > 0) {
				System.out.println(sql1a);
				psA = conn.prepareStatement(sql1a);
				psA.setInt(1, refIdx);
				cnt = psA.executeUpdate();				
			}
			
			if (cnt > 0) {
				System.out.println(sql2);
				ps1 = conn.prepareStatement(sql2);
				ps1.setInt(1, MemCache.getChangeLogKey());
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
				if (psA != null) {psA.close();}
				if (ps1 != null) {ps1.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return cnt;
	}	
}
