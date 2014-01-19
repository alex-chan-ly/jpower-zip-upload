package com.jpower.cms.db.storedprocedures;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;

public class ApplicationDetailMaterialValidator {
//	create PROCEDURE CHECK_APPLICATION_UPLOAD_SUB_SERIES(in id int )
//	  PARAMETER STYLE JAVA
//	  LANGUAGE JAVA
//	    EXTERNAL NAME 'com.jpower.cms.db.storedprocedures.ApplicationSubSeriesValidator.checkExistForAdd';
	
//	create PROCEDURE CHECK_APPLICATION_UPLOAD_SUB_SERIES(in id int, out cnt int)
//	  PARAMETER STYLE JAVA
//	  LANGUAGE JAVA
//	    EXTERNAL NAME 'com.jpower.cms.db.storedprocedures.ApplicationSubSeriesValidator.checkExistForAdd';

//	Execute Main1.execute_xxx()
		
	public static int checkExistForAdd(int uploadSeq) {
		Connection conn = null;
		PreparedStatement ps1 = null;
		int recCount = 0;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION_DETAIL', ");
		sb.append("'Validation error (Material already exists for addition) - ' || a.sub_series_id || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application_detail a where a.ref_idx = ? and a.tran_action = 'ADD' ");
		sb.append("and exists (select * from jpt_material b where b.rec_status = 'ACT' and b.material_id = a.sub_series_id)");

		try {
//			conn = DriverManager.getConnection("jdbc:default:connection");
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

	public static int checkNotExistForDel(int uploadSeq) {
		Connection conn = null;
		PreparedStatement ps1 = null;
		int recCount = 0;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION_DETAIL', ");
		sb.append("'Validation error (Material not exists for deletion) - ' || a.sub_series_id || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application_detail a where a.ref_idx = ? and a.tran_action = 'DEL' ");
		sb.append("and not exists (select * from jpt_material b where b.rec_status = 'ACT' and b.material_id = a.sub_series_id)");
		
		try {
//			conn = DriverManager.getConnection("jdbc:default:connection");
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
}
