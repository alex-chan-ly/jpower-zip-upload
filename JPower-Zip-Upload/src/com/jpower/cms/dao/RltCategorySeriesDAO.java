package com.jpower.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.MemCache;

public class RltCategorySeriesDAO {

	public static String sql1 = "insert into jpt_rlt_category_series (ref_idx, category_pk, series_pk, cat_series_seq, rec_status, create_date, update_date, create_user, update_user) "
			+ "select drv.ref_idx, master_cat.category_pk, master_series.series_pk, drv.series_seq, 'ACT', current_timestamp, current_timestamp, 'john', 'john' from "
			+ "(select distinct ref_idx, category, series, series_seq from jpw_application where tran_action = 'ADD' and tran_status = 'AWV' and ref_idx = ?) drv,"
			+ "(select category_pk, category_id from jpt_category where rec_status = 'ACT') master_cat,"
			+ "(select series_pk, series_id from jpt_series where rec_status = 'ACT') master_series "
			+ "where drv.category = master_cat.category_id and drv.series = master_series.series_id "
			+ "and not exists (select * from jpt_rlt_category_series core where core.category_pk = master_cat.category_pk and core.series_pk = master_series.series_pk "
			+ "and core.cat_series_seq = drv.series_seq)";
			
	public static String sql2 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'ADDITION-RLT-CATEGORY_SERIES', "
			+ "'Category_pk : ' || TRIM(CAST(CAST(category_pk AS CHAR(10))AS VARCHAR(10))) || '; Series_pk : ' || TRIM(CAST(CAST(series_pk AS CHAR(10))AS VARCHAR(10))) || '; Category_Series_Seq : ' || TRIM(CAST(CAST(cat_series_seq AS CHAR(10))AS VARCHAR(10))) || ' being added', "
			+ "'', current_timestamp, current_timestamp from jpt_rlt_category_series where ref_idx = ? and rec_status = 'ACT'";

	public static String sql3 = "update jpt_rlt_category_series set ref_idx = ?, rec_status = 'DEL', update_date = current_timestamp where rec_status = 'ACT' "
			+ "and series_pk in (select mast_s.series_pk from jpt_series mast_s where mast_s.rec_status = 'DEL' and mast_s.ref_idx = ? )";
	
	public static String sql4 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-RLT-CATEGORY_SERIES', " 
			+ "'Category_Series_pk : ' || TRIM(CAST(CAST(category_series_pk AS CHAR(10))AS VARCHAR(10))) || '; Category_pk : ' || TRIM(CAST(CAST(category_pk AS CHAR(10))AS VARCHAR(10))) || '; Series_pk : ' || TRIM(CAST(CAST(series_pk AS CHAR(10))AS VARCHAR(10))) "
			+ "|| '; Category_Series_seq : ' || TRIM(CAST(CAST(cat_series_seq AS CHAR(10))AS VARCHAR(10))) || ' being deleted', '', "
			+ "current_timestamp, current_timestamp from jpt_rlt_category_series where ref_idx = ? and rec_status = 'DEL'";
	
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
				ps1 = conn.prepareStatement(sql2);
//				ps1.setInt(1, MemCache.getChangeLogKey());
				ps1.setInt(1, refIdx);
				ps1.setInt(2, refIdx);
				cnt = ps1.executeUpdate();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (ps != null) {ps.close();}
				if (ps1 != null) {ps.close();}
				if (conn != null) {conn.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return cnt;
	}	
}
