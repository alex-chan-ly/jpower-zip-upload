package com.jpower.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.MemCache;

public class LobDAO {
	
	public static String sql1 = "insert into jpt_lob (ref_idx, lob_id, sub_lob_id, sub_lob_label_eng, sub_lob_label_chin, rec_status, create_date, update_date, create_user, update_user) "
			+ "select distinct ref_idx, app.lob, app.sub_lob, app.sub_lob_label_eng, app.sub_lob_label_chin, 'ACT', "
			+ "current_timestamp, current_timestamp, 'john', 'john' from jpw_application app where app.tran_action = 'ADD' and app.tran_status = 'AWV' and app.ref_idx = ?"
			+ "and not exists (select * from jpt_lob l where l.lob_id = app.lob and l.sub_lob_id = app.sub_lob and l.rec_status = 'ACT')";
	
	public static String sql2 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'ADDITION-LOB', "
			+ "'LOB_pk : ' || TRIM(CAST(CAST(lob_pk AS CHAR(10))AS VARCHAR(10))) || '; LOB_ID : ' || LOB_id || '; SUB_LOB_ID : ' || sub_lob_id || ' being added', '', "
			+ "current_timestamp, current_timestamp from jpt_lob where rec_status = 'ACT' and ref_idx = ?";
	
	public static String sql3 = "update jpt_lob set ref_idx = ?, rec_status = 'DEL', update_date = current_timestamp where rec_status = 'ACT' and lob_pk in ("
			+ "select distinct lob_pk from jpt_rlt_lob_category where rec_status = 'DEL' and ref_idx= ? except "
			+ "select distinct lob_pk from jpt_rlt_lob_category where rec_status = 'ACT' and lob_pk in ("
			+ "select distinct lob_pk from jpt_rlt_lob_category where rec_status = 'DEL' and ref_idx= ?))";
		
	public static String sql4 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-LOB', " 
			+ "'Lob_pk : ' || TRIM(CAST(CAST(lob_pk AS CHAR(10))AS VARCHAR(10))) || '; LOB ID : ' || lob_id || '; Sub LOB ID : ' || sub_lob_id  || ' being deleted', '', "
			+ "current_timestamp, current_timestamp from jpt_lob where rec_status = 'DEL' and ref_idx = ?";

	

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
}
