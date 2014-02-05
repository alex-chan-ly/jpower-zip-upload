package com.jpower.cms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.vo.LogVO;

public class LogDAO {
	
	public static String sql1 = "select ref_no, severity, category, log_message, remarks_1, create_date "
		+ "from jpt_log where ref_no = ? order by severity, category, create_date desc";
	
	public static List<LogVO> extractLogDetailByRefIdx(int refIdx) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<LogVO> voList = new ArrayList<LogVO>();
		
		conn = DBAccess.getDBConnection();
		try {
			ps = conn.prepareStatement(sql1);
			ps.setString(1, String.valueOf(refIdx));
			rs = ps.executeQuery();
			
			while (rs.next()) {
				LogVO vo = new LogVO();
				vo.setRefIdx(rs.getString(1));
				vo.setSeverity(rs.getString(2));
				vo.setCategory(rs.getString(3));
				vo.setLogMessage(rs.getString(4));
				vo.setRemarks1(rs.getString(5));
				vo.setCreateDate(rs.getTimestamp(6));
				
				voList.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) {rs.close();}
				if (ps != null) {ps.close();}
				if (conn != null) {conn.close();}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		return voList;
	}
}
