package com.jpower.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.upload.common.MemCache;

public class RltLobCategoryDAO {

	public static String sql1 = "insert into jpt_rlt_lob_category (ref_idx, lob_pk, category_pk, lob_category_seq, rec_status, create_date, update_date, create_user, update_user) "
			+ "select drv.ref_idx, master_lob.lob_pk, master_cat.category_pk, drv.category_seq, 'ACT', current_timestamp, current_timestamp, 'john', 'john' from "
			+ "(select distinct ref_idx, lob, sub_lob, category, category_seq from jpw_application where tran_action = 'ADD' and tran_status = 'AWV' and ref_idx = ?) drv,"
			+ "(select lob_pk, lob_id, sub_lob_id from jpt_lob where rec_status = 'ACT') master_lob,"
			+ "(select category_pk, category_id from jpt_category where rec_status = 'ACT') master_cat "
			+ "where drv.lob = master_lob.lob_id and drv.sub_lob = master_lob.sub_lob_id and drv.category = master_cat.category_id "
			+ "and not exists (select * from jpt_rlt_lob_category core where core.lob_pk = master_lob.lob_pk and core.category_pk = master_cat.category_pk "
			+ "and core.lob_category_seq = drv.category_seq)";
			
	public static String sql2 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'ADDITION-RLT-LOB_CATEGORY', " 
			+ "'LOB_pk : ' || TRIM(CAST(CAST(lob_pk AS CHAR(10))AS VARCHAR(10))) || '; Category_pk : ' || TRIM(CAST(CAST(category_pk AS CHAR(10))AS VARCHAR(10))) || '; Lob_Category_Seq : ' || TRIM(CAST(CAST(lob_category_seq AS CHAR(10))AS VARCHAR(10))) || ' being added', "
			+ "'', current_timestamp, current_timestamp from jpt_rlt_lob_category where ref_idx = ? and rec_status = 'ACT'";
			

	public static String sql3 = "update jpt_rlt_lob_category set ref_idx = ?, rec_status = 'DEL', update_date = current_timestamp where rec_status = 'ACT' "
			+ "and category_pk in (select mast_cat.category_pk from jpt_category mast_cat where mast_cat.rec_status = 'DEL' and mast_cat.ref_idx = ? )";
	
	public static String sql4 = "insert into jpt_log (ref_no, severity, category, log_message, remarks_1, create_date, update_date) "
			+ "select TRIM(CAST(CAST(? AS CHAR(10))AS VARCHAR(10))), 'Info', 'DELETION-RLT-LOB_CATEGORY', "
			+ "'Lob_Category_pk : ' || TRIM(CAST(CAST(lob_category_pk AS CHAR(10))AS VARCHAR(10))) || '; Lob_pk : ' || TRIM(CAST(CAST(lob_pk AS CHAR(10))AS VARCHAR(10))) || '; Category_pk : ' || TRIM(CAST(CAST(category_pk AS CHAR(10))AS VARCHAR(10))) "
			+ "|| '; Lob_Category_seq : ' || TRIM(CAST(CAST(lob_category_seq AS CHAR(10))AS VARCHAR(10))) || ' being deleted', '', " 
			+ "current_timestamp, current_timestamp from jpt_rlt_lob_category where ref_idx = ? and rec_status = 'DEL'";
	
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
