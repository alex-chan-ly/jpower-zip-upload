package com.jpower.cms.db.storedprocedures;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubSeries {
	public static void checkMaterialID(int refIdx) {
		Connection conn;
		int cnt = 0;
		try {
			conn = DriverManager.getConnection("jdbc:default:connection");
//			String s1 = "select count(*) from jpw_application where ref_idx = " + refIdx;
			String s1 = "INSERT INTO A1 VALUES ('" + refIdx + "')";
			PreparedStatement ps1 = conn.prepareStatement(s1);
            ps1.executeUpdate();
//            rs.next();
//            if (rs.next()){
//            	cnt = rs.getInt(1);
//            	System.out.println("StoredProcedure : " + cnt);
//            }
//            rs.close();
            ps1.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
