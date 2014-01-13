package com.jpower.cms.upload.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import com.jpower.cms.vo.InventoryApplicationDetailVO;
import com.jpower.cms.vo.InventoryApplicationVO;

public class InventoryApplicationDetailDBProcessor {
       
        public static String insertSql = "insert into JPW_APPLICATION_DETAIL(SUB_SERIES_ID, REF_NO, SERIES, AVALIABLE_SIZE, TILE_THICKNESS, COLOR, " +
                        "FINISHING, APPLICATION, REMARKS_1, REMARKS_2, REMARKS_3, TRAN_ACTION, TRAN_STATUS, CREATE_USER, UPDATE_USER, " +
                        "CREATE_DATE, UPDATE_DATE, REF_IDX, EXCEL_ROW_ID, EXCEL_ROW_DATA) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,current_timestamp,?,?,?)";
       
        public static void saveToDB(List<InventoryApplicationDetailVO> voList) {
                PreparedStatement psInsert;
                Connection conn = DBAccess.getDBConnection();
                try {
                        psInsert = conn.prepareStatement(insertSql);
                        for (Iterator<InventoryApplicationDetailVO> i = voList.iterator(); i.hasNext();) {
                                InventoryApplicationDetailVO vo = (InventoryApplicationDetailVO) i.next();
                               
                                psInsert.setString(1, vo.getSubSeriesId());
                                psInsert.setString(2, vo.getRefNo());
                                psInsert.setString(3, vo.getSeries());
                                psInsert.setString(4, vo.getAvailableSize());
                                psInsert.setString(5, vo.getTileThickness());
                                psInsert.setString(6, vo.getColor());
                                psInsert.setString(7, vo.getFinishing());
                                psInsert.setString(8, vo.getApplication());
                                psInsert.setString(9, vo.getRemarks1());
                                psInsert.setString(10, vo.getRemarks2());
                                psInsert.setString(11, vo.getRemarks3());
                                psInsert.setString(12, vo.getTranAction().toUpperCase());
                                psInsert.setString(13, Util.TRAN_STATUS_AWV);
                                psInsert.setString(14, Util.USER_ID_DEFAULT);
                                psInsert.setString(15, Util.USER_ID_DEFAULT);
                                psInsert.setInt(16,  MemCache.getUploadSeq());
                                psInsert.setInt(17, vo.getExcelRowID());
                                psInsert.setString(18, vo.getExcelRowData());
                                psInsert.executeUpdate();
                        }
                       
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
               
        }
}
