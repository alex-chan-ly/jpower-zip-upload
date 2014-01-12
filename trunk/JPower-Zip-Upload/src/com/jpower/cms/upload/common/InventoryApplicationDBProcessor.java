package com.jpower.cms.upload.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import com.jpower.cms.dao.InventoryApplicationVO;

public class InventoryApplicationDBProcessor {
       
        public static String insertSql = "insert into JPW_APPLICATION(LOB, SUB_LOB, CATEGORY, CATEGORY_SEQ, CATEGORY_LABEL_ENG, CATEGORY_LABEL_CHIN," +
                        "CATEGORY_IMAGE, SERIES, SERIES_SEQ, SERIES_LABEL_ENG, SERIES_LABEL_CHIN, SERIES_IMAGE_SMALL, SERIES_IMAGE_LARGE, SUB_SERIES, " +
                        "SUB_SERIES_SEQ, SUB_SERIES_ID, SUB_SERIES_IMAGE_SMALL, SUB_SERIES_IMAGE_LARGE, TRAN_ACTION, TRAN_STATUS, CREATE_USER, UPDATE_USER, " +
                        "CREATE_DATE, UPDATE_DATE, REF_IDX, EXCEL_ROW_ID, EXCEL_ROW_DATA) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,current_timestamp,?,?,?)";
       
        public static void saveToDB(List<InventoryApplicationVO> voList) {
                PreparedStatement psInsert;
                Connection conn = DBAccess.getDBConnection();
                try {
                        psInsert = conn.prepareStatement(insertSql);
                        for (Iterator<InventoryApplicationVO> i = voList.iterator(); i.hasNext();) {
                                InventoryApplicationVO vo = (InventoryApplicationVO) i.next();
                                psInsert.setString(1, vo.getLob());
                                psInsert.setString(2, vo.getSubLob());
                                psInsert.setString(3, vo.getCategory());
                                psInsert.setInt(4, vo.getCategorySeq());
                                psInsert.setString(5, vo.getCategoryLabelEng());
                                psInsert.setString(6, vo.getCategoryLabelChin());
                                psInsert.setString(7, vo.getCategoryImagePath());
                                psInsert.setString(8, vo.getSeries());
                                psInsert.setInt(9, vo.getSubSeriesSeq());
                                psInsert.setString(10, vo.getSeriesLabelEng());
                                psInsert.setString(11, vo.getSeriesLabelChin());
                                psInsert.setString(12, vo.getSeriesImageSmallPath());
                                psInsert.setString(13, vo.getSeriesImageLargePath());
                                psInsert.setString(14, vo.getSubSeries());
                                psInsert.setInt(15, vo.getSubSeriesSeq());
                                psInsert.setString(16, vo.getSubSeriesID());
                                psInsert.setString(17, vo.getSubSeriesImageSmallPath());
                                psInsert.setString(18, vo.getSubSeriesImageLargePath());
                                psInsert.setString(19, vo.getTranAction().toUpperCase());
                                psInsert.setString(20, Util.TRAN_STATUS_AWV);
                                psInsert.setString(21, Util.USER_ID_DEFAULT);
                                psInsert.setString(22, Util.USER_ID_DEFAULT);
                                psInsert.setInt(23, MemCache.getUploadSeq());
                                psInsert.setInt(24, vo.getExcelRowID());
                                psInsert.setString(25, vo.getExcelRowData());
                                psInsert.executeUpdate();
                        }
                       
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
               
        }
}

