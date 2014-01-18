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
       
        public static String insertSql = "insert into JPW_APPLICATION_DETAIL(SUB_SERIES_ID, SERIES, NAME, DESCRIPTION_ENG, AVALIABLE_SIZE, TILE_THICKNESS, COLOR, " +
                        "FINISHING, APPLICATION, REMARKS_1, REMARKS_2, REMARKS_3, TRAN_ACTION, TRAN_STATUS, CREATE_USER, UPDATE_USER, " +
                        "CREATE_DATE, UPDATE_DATE, REF_IDX, EXCEL_ROW_ID, EXCEL_ROW_DATA) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,current_timestamp,?,?,?)";
       
        public static void saveToDB(List<InventoryApplicationDetailVO> voList) {
                PreparedStatement psInsert;
                Connection conn = DBAccess.getDBConnection();
                try {
                        psInsert = conn.prepareStatement(insertSql);
                        for (Iterator<InventoryApplicationDetailVO> i = voList.iterator(); i.hasNext();) {
                                InventoryApplicationDetailVO vo = (InventoryApplicationDetailVO) i.next();
                               
                                psInsert.setString(1, vo.getSubSeriesId());
                                psInsert.setString(2, vo.getSeries());
                                psInsert.setString(3, vo.getName());
                                psInsert.setString(4, vo.getDescriptionEng());
                                psInsert.setString(5, vo.getAvailableSize());
                                psInsert.setString(6, vo.getTileThickness());
                                psInsert.setString(7, vo.getColor());
                                psInsert.setString(8, vo.getFinishing());
                                psInsert.setString(9, vo.getApplication());
                                psInsert.setString(10, vo.getRemarks1());
                                psInsert.setString(11, vo.getRemarks2());
                                psInsert.setString(12, vo.getRemarks3());
                                psInsert.setString(13, vo.getTranAction().toUpperCase());
                                psInsert.setString(14, Util.TRAN_STATUS_AWV);
                                psInsert.setString(15, Util.USER_ID_DEFAULT);
                                psInsert.setString(16, Util.USER_ID_DEFAULT);
                                psInsert.setInt(17,  MemCache.getUploadSeq());
                                psInsert.setInt(18, vo.getExcelRowID());
                                psInsert.setString(19, vo.getExcelRowData());
                                psInsert.executeUpdate();
                        }
                       
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
               
        }
}
