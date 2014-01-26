package com.jpower.cms.db.storedprocedures;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;

public class ApplicationSubSeriesValidator {
	
//	create PROCEDURE CHECK_APPLICATION_UPLOAD_SUB_SERIES(in id int )
//	  PARAMETER STYLE JAVA
//	  LANGUAGE JAVA
//	    EXTERNAL NAME 'com.jpower.cms.db.storedprocedures.ApplicationSubSeriesValidator.checkExistForAdd';
	
//	create PROCEDURE CHECK_APPLICATION_UPLOAD_SUB_SERIES(in id int, out cnt int)
//	  PARAMETER STYLE JAVA
//	  LANGUAGE JAVA
//	    EXTERNAL NAME 'com.jpower.cms.db.storedprocedures.ApplicationSubSeriesValidator.checkExistForAdd';

//	Execute Main1.execute_3()
		
	public static int checkExistForAdd(int uploadSeq) {
		Connection conn = null;
		PreparedStatement ps1 = null;
		int recCount = 0;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10))AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Validation error (Sub series already exists for addition) - ' || a.sub_series_id || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and a.tran_action = 'ADD' ");
		sb.append("and exists (select * from jpt_sub_series b where b.rec_status = 'ACT' and b.material_id = a.sub_series_id)");

		try {
//			conn = DriverManager.getConnection("jdbc:default:connection");
			conn = DBAccess.getDBConnection();
			ps1 = conn.prepareStatement(sb.toString());
			ps1.setInt(1, uploadSeq);
			recCount = ps1.executeUpdate();
	
		} catch (SQLException e) {
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
	
//	create PROCEDURE CHECK_APPLICATION_UPLOAD_SUB_SERIES_FOR_DEL(in id int )
//	  PARAMETER STYLE JAVA
//	  LANGUAGE JAVA
//	    EXTERNAL NAME 'com.jpower.cms.db.storedprocedures.ApplicationSubSeriesValidator.checkNotExistForDel';
	
//	create PROCEDURE CHECK_APPLICATION_UPLOAD_SUB_SERIES_FOR_DEL(in id int, out cnt int)
//	  PARAMETER STYLE JAVA
//	  LANGUAGE JAVA
//	    EXTERNAL NAME 'com.jpower.cms.db.storedprocedures.ApplicationSubSeriesValidator.checkNotExistForDel';

//	Execute Main1.execute_?()	
	
	public static int checkNotExistForDel(int uploadSeq) {
		Connection conn = null;
		PreparedStatement ps1 = null;
		int rtnCount = 0;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10))AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Validation error (Sub series not exists for deletion) - ' || a.sub_series_id || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and a.tran_action = 'DEL' ");
		sb.append("and not exists (select * from jpt_sub_series b where b.rec_status = 'ACT' and b.material_id = a.sub_series_id)");

		try {
//			conn = DriverManager.getConnection("jdbc:default:connection");
			conn = DBAccess.getDBConnection();
			ps1 = conn.prepareStatement(sb.toString());
			ps1.setInt(1, uploadSeq);
			rtnCount = ps1.executeUpdate();
		
		} catch (SQLException e) {
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
		return rtnCount;		
	}	
	
	public static int checkSubSeriesImageSmallNameWithSpaceChar(int uploadSeq) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Sub series small image file name contain space character - ' || a.sub_series_image_small || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and locate(' ', a.sub_series_image_small) > 0");

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

	public static int checkSubSeriesImageLargeNameWithSpaceChar(int uploadSeq) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Sub series large image file name contain space character - ' || a.sub_series_image_large || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and locate(' ', a.sub_series_image_large) > 0");

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
	
	public static int checkSubSeriesImageSmallExists(int uploadSeq, String prefix, String filePath) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Sub series small image file name not found within upload package - ' || a.sub_series_image_small || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and tran_action = 'ADD' ");
		sb.append("and is_file_exists(?, ?, a.sub_series_image_small) = -1");

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

	public static int checkSubSeriesImageLargeExists(int uploadSeq, String prefix, String filePath) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Sub series large image file name not found within upload package - ' || a.sub_series_image_large || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and tran_action = 'ADD' ");
		sb.append("and is_file_exists(?, ?, a.sub_series_image_large) = -1");

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

	
}
