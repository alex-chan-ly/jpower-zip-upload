package com.jpower.cms.upload.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
       
        public static int getUploadSequence() {
                int i = 0;
                ResultSet rs = null;
                Statement s = null;
                Connection conn = DBAccess.getDBConnection();
                try {
                        s = conn.createStatement();
                        rs = s.executeQuery("select next value for JPS_UPLOAD_SEQ FROM SYSIBM.SYSDUMMY1");
                        while (rs.next()) {
                                i = rs.getInt(1);              
                                System.out.println("inside getSeq");
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                finally {
                        try {
                                if (rs != null) {rs.close();}
                                if (s != null) {s.close();}
                                if (conn != null) {conn.close();}
                        } catch(SQLException e) {
                                e.printStackTrace();
                        }
                }
                return i;
        }
}
