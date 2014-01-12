package com.jpower.cms.upload.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccess {
       
        public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
//      public static String dbName="jPowerDB";
        public static String dbName="db1";
        public static String connectionURL = "jdbc:derby:" + dbName + ";create=true";
        public static Connection conn = null;
       
        private static Connection acquireDBConnection() {
                try {
                        Class.forName(driver);
                       
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                }
               
                try {
                        conn = DriverManager.getConnection(connectionURL);
                } catch (SQLException e) {
                        e.printStackTrace();
                }
               
                return conn;
        }
       
        public static Connection getDBConnection() {
                if (conn == null) {
                        return acquireDBConnection();
                } else {
                        return conn;
                }
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

