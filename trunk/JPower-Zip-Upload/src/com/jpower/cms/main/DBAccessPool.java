package com.jpower.cms.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import biz.source_code.miniConnectionPoolManager.MiniConnectionPoolManager;

public class DBAccessPool {
	
	public static MiniConnectionPoolManager poolMgr = null;

	public static void test1() {
		org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource dataSource = new org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource();
		dataSource.setDatabaseName("/home/alexc/workspace_ee/JPower-Zip-Upload/db2");
//		dataSource.setCreateDatabase("create");
		poolMgr = new MiniConnectionPoolManager(dataSource, 2);
		System.out.println("Active connection : " + poolMgr.getActiveConnections());
	}
	
	public static void test2() {
		try {
			System.out.println("test2");
			Connection conn = poolMgr.getConnection();
			conn = poolMgr.getConnection();
			conn.close();
			conn = poolMgr.getConnection();
			
			System.out.println("Active connection : " + poolMgr.getActiveConnections());
			String sql = "select sub_lob_id from jpt_lob";
			Statement st = conn.createStatement();
			ResultSet rs =  st.executeQuery(sql);
			 while (rs.next()) {
                 System.out.println(rs.getString(1));              
                 
         }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		test1();
		test2();
	}
}
