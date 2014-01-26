package com.jpower.cms.db.storedprocedures;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpower.cms.upload.common.DBAccess;

public class ApplicationSeriesValidator {
	
	
	public static int checkDuplicateSubSeriesSequence(int uploadSeq) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select distinct TRIM(CAST(CAST(a.ref_idx AS CHAR(10))AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Validation error (Series has duplicate Sub Series Sequence)', 'Series : ' || a.series ");
		sb.append(", current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and exists (");
		sb.append("select count(*) from jpw_application b where b.ref_idx = ? and b.series = a.series ");
		sb.append("group by b.series, b.sub_series_seq having count(*) > 1)");

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
	
	public static int checkInconsistentSeriesSequence(int uploadSeq) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select distinct TRIM(CAST(CAST(main.ref_idx AS CHAR(10))AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Validation error (Series has inconsistent series sequence)', 'Series : ' || main.series ");
		sb.append(", current_timestamp,current_timestamp from jpw_application main where main.ref_idx = ? and main.series in ( ");
		sb.append("select a.series from (select  b.series, b.series_seq from jpw_application b  where b.ref_idx = ?");
		sb.append("group by b.series, b.series_seq ) a group by a.series having count(*) >1)");

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
	
	public static int checkNotExistForDel(int uploadSeq) {
		Connection conn = null;
		PreparedStatement ps1 = null;
		int recCount = 0;
				
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
	
	public static int checkSeriesImageSmallNameWithSpaceChar(int uploadSeq) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Series small image file name contain space character - ' || a.series_image_small || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and locate(' ', a.series_image_small) > 0");

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
	
	public static int checkSeriesImageLargeNameWithSpaceChar(int uploadSeq) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Series large image file name contain space character - ' || a.series_image_large || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and locate(' ', a.series_image_large) > 0");

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
	
	public static int checkSeriesImageSmallExists(int uploadSeq, String prefix, String filePath) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Series small image file name not found within upload package - ' || a.series_image_small || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and tran_action = 'ADD' ");
		sb.append("and is_file_exists(?, ?, a.series_image_small) = -1");

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
	
	public static int checkSeriesImageLargeExists(int uploadSeq, String prefix, String filePath) {
		Connection conn = null;
		int recCount = 0;
		PreparedStatement ps1 = null;
				
		StringBuffer sb = new StringBuffer();
		sb.append("insert into jpt_log(ref_no, severity, category, log_message, remarks_1, create_date, update_date) ");
		sb.append("select TRIM(CAST(CAST(a.ref_idx AS CHAR(10)) AS VARCHAR(10))), 'Error', 'UPLOAD-APPLICATION', ");
		sb.append("'Series large image file name not found within upload package - ' || a.series_image_large || ', Excel row number : ' || TRIM(CAST(CAST(a.excel_row_id AS CHAR(10)) AS VARCHAR(10))), ");
		sb.append("a.excel_row_data, current_timestamp,current_timestamp from jpw_application a where a.ref_idx = ? and tran_action = 'ADD' ");
		sb.append("and is_file_exists(?, ?, a.series_image_large) = -1");

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
