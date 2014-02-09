package com.jpower.cms.upload.common;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DBAccessSimple implements ServletContextListener {

	public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	// public static String dbName="jPowerDB";
	public static String dbName = "/home/alexc/workspace_ee/JPower-Web/JPower/content/db";
	public static String connectionURL = "jdbc:derby:" + dbName
			+ ";create=true";
	public static Connection conn = null;

//	private static String dataBaseName = FileHelper
//			.getConfigProperty("db.databaseName");

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

	private static void deregisterJDBCDriver() {
		Enumeration<Driver> drivers = DriverManager.getDrivers();

		System.out.println("deregister JDBCDriver ...");

		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				System.out.println("deregistering jdbc driver: " + driver);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void shutdownDB() {
		boolean gotSQLExc = false;
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
//			DriverManager.getConnection("jdbc:derby:;shutdown=true;deregister=false");
		} catch (SQLException se) {
			if (se.getSQLState().equals("XJ015")) {
				gotSQLExc = true;
			}
		}
		if (!gotSQLExc) {
			System.out.println("Database did not shut down normally");
		} else {
			System.out.println("Database shut down normally");
		}
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		shutdownDB();
		deregisterJDBCDriver();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}
}
