package com.jpower.cms.upload.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource;

import biz.source_code.miniConnectionPoolManager.MiniConnectionPoolManager;

public class DBAccessJPowerBkup {
	
		private static MiniConnectionPoolManager poolMgr = null;
//		private static String dataBaseName = "db2";
//		private static String dataBaseName = "/home/alexc/Documents/_jPower/_fromJpowerSite/db_bkup/db1/";
//      public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
//      public static String dbName="db1";
//      public static String connectionURL = "jdbc:derby:" + dbName + ";create=true";
		private static String dataBaseName = FileHelper.getConfigProperty("db.databaseName");

       
        public static Connection conn = null;
       
        private static Connection acquireDBConnection() {
        	Connection conn = null;
        	
        	int dbPoolSize = Integer.parseInt(FileHelper.getConfigProperty("db.poolSize"));
        	
    		EmbeddedConnectionPoolDataSource dataSource = new EmbeddedConnectionPoolDataSource();
    		dataSource.setDatabaseName(dataBaseName);
    		poolMgr = new MiniConnectionPoolManager(dataSource, dbPoolSize);
            
    		try {
				conn =  poolMgr.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		return conn;
        }
       
        public static Connection getDBConnection() {
        	Connection conn = null;
        	try {
                if (poolMgr == null) {
                    conn = acquireDBConnection();
            } else {
                    conn = poolMgr.getConnection();
            }        		
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        	
        	return conn;
        }
       
        public static void shutdownDB() {
                boolean gotSQLExc = false;
        try {
           DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException se)  {    
           if ( se.getSQLState().equals("XJ015") ) {            
              gotSQLExc = true;
           }
        }
        if (!gotSQLExc) {
                System.out.println("Database did not shut down normally");
        }  else  {
           System.out.println("Database shut down normally");  
        }  
        }
}

