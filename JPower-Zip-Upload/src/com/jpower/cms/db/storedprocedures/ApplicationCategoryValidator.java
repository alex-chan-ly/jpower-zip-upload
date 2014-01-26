package com.jpower.cms.db.storedprocedures;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;

public class ApplicationCategoryValidator {
		
	public static int checkInconsistentCategorySequence(int uploadSeq) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select distinct TRIM(CAST(CAST(main.ref_idx AS CHAR(10))AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Validation error (Category has inconsistent category sequence)', 'Category : ' || main.category ");
		sb.append(", current_timestamp,current_timestamp from jpw_application main where main.ref_idx = ? and main.category in ( ");
		sb.append("select a.category from (select  b.category, b.category_seq from jpw_application b  where b.ref_idx = ?");
		sb.append("group by b.category, b.category_seq ) a group by a.category having count(*) > 1)");

		try {
			conn = DBAccess.getDBConnection();
			ps1 = conn.prepareStatement(sb.toString());
			ps1.setInt(1, uploadSeq);
			ps1.setInt(2, uploadSeq);
			recCount = ps1.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (ps1 != null) {ps1.close();}
				if (conn != null) {conn.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		return recCount;
	}
	
	public static int checkCategoryImageNameWithSpaceChar(int uploadSeq) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Category image file name contain space character - ' || a.category_image || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and locate(' ', a.category_image) > 0");

		try {
			conn = DBAccess.getDBConnection();
			ps1 = conn.prepareStatement(sb.toString());
			ps1.setInt(1, uploadSeq);
			recCount = ps1.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (ps1 != null) {ps1.close();}
				if (conn != null) {conn.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		return recCount;
	}
	
	public static int checkCategoryImageExists(int uploadSeq, String prefix, String filePath) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Category image file name not found within upload package - ' || a.category_image || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and tran_action = 'ADD' ");
		sb.append("and is_file_exists(?, ?, a.category_image) = -1");

		try {
			conn = DBAccess.getDBConnection();
			ps1 = conn.prepareStatement(sb.toString());
			ps1.setInt(1, uploadSeq);
			ps1.setString(2, prefix);
			ps1.setString(3, filePath);
			recCount = ps1.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (ps1 != null) {ps1.close();}
				if (conn != null) {conn.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		return recCount;
	}
	
//	public static void checkNotExistForDel(int uploadSeq, int[] recCount) {
//		Connection conn;
//		PreparedStatement ps1 = null;
//				
//		StringBuffer sb = new StringBuffer();
//		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
//		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10))AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
//		sb.append("'Validation error (Sub series not exists for deletion) - ' || a.sub_series_id || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
//		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and a.tran_action = 'DEL' ");
//		sb.append("and not exists (select * from jpt_sub_series b where b.rec_status = 'ACT' and b.material_id = a.sub_series_id)");
//
//		try {
//			conn = DriverManager.getConnection("jdbc:default:connection");
//			ps1 = conn.prepareStatement(sb.toString());
//			ps1.setInt(1, uploadSeq);
//			int cnt = ps1.executeUpdate();
//			
//			recCount[0] = cnt;
//		
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally {
//			try {
//				if (ps1 != null) {ps1.close();}
//			} catch(SQLException e) {
//				e.printStackTrace();
//			}
//		}		
//	}	
}
