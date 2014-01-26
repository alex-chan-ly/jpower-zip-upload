package com.jpower.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.MemCache;

public class MaterialDAO {
	
	public static String sql1 = "update jpt_material set ref_idx = ?, rec_status = 'DEL', update_date = current_timestamp where rec_status = 'ACT' and material_id in ("
			+ "select sub_series_id from jpw_application_detail where tran_action = 'DEL' and ref_idx = ?)";
	
	public static String sql2 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-APPLICATION_DETAIL', "
			+ "'Material id : ' || sub_series_id || ' being marked deletion', '', current_timestamp, current_timestamp from jpw_application_detail where tran_action = 'DEL' and ref_idx = ?";
	
	public static String sql3 = "insert into jpt_material(ref_idx, material_id, series, name, description_eng, description_chin, avaliable_size, avaliable_size_chin, tile_thickness, tile_thickness_chin, color, color_chin, "
			+ "finishing, finishing_chin, application, application_chin, remarks_1, remarks_1_chin, rec_status, create_date, update_date, create_user, update_user) " 
			+ "select ref_idx, sub_series_id, series, name, description_eng, description_chin, avaliable_size, avaliable_size_chin, tile_thickness, tile_thickness_chin, color, color_chin, finishing, finishing_chin, application, application_chin, "
			+ "remarks_1, remarks_1_chin, 'ACT', current_timestamp, current_timestamp, 'john', 'john' from jpw_application_detail where tran_action = 'ADD' and tran_status = 'AWV' and ref_idx = ?";
	
	public static String sql4 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) " 
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'ADDITION-MATERIAL', "
			+ "'Material id : ' || sub_series_id || ' being added', '', current_timestamp, current_timestamp from jpw_application_detail "
			+ "where tran_action = 'ADD' and tran_status = 'AWV' and ref_idx = ?";
	
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
}
