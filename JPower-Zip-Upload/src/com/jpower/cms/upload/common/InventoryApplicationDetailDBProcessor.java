package com.jpower.cms.upload.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.jpower.cms.vo.InventoryApplicationDetailVO;

public class InventoryApplicationDetailDBProcessor {
       
        public static String insertSql = "insert into JPW_APPLICATION_DETAIL(SUB_SERIES_ID, SERIES, NAME, DESCRIPTION_ENG, DESCRIPTION_CHIN, AVALIABLE_SIZE, AVALIABLE_SIZE_CHIN, TILE_THICKNESS, TILE_THICKNESS_CHIN, COLOR, COLOR_CHIN, " +
                        "FINISHING, FINISHING_CHIN, APPLICATION, APPLICATION_CHIN, REMARKS_1, REMARKS_1_CHIN, REMARKS_2, REMARKS_2_CHIN, REMARKS_3, REMARKS_3_CHIN, TRAN_ACTION, TRAN_STATUS, CREATE_USER, UPDATE_USER, " +
                        "CREATE_DATE, UPDATE_DATE, REF_IDX, EXCEL_ROW_ID, EXCEL_ROW_DATA) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,current_timestamp,?,?,?)";
       
        public static PreparedStatement psInsert = null;
        
        public static void saveToDB(List<InventoryApplicationDetailVO> voList, int refIdx) {
                
                Connection conn = DBAccess.getDBConnection();
                try {
                        psInsert = conn.prepareStatement(insertSql);
                        for (Iterator<InventoryApplicationDetailVO> i = voList.iterator(); i.hasNext();) {
                                InventoryApplicationDetailVO vo = (InventoryApplicationDetailVO) i.next();
                               
                                psInsert.setString(1, vo.getSubSeriesId());
                                psInsert.setString(2, vo.getSeries());
                                psInsert.setString(3, vo.getName());
                                psInsert.setString(4, vo.getDescriptionEng());
                                psInsert.setString(5, vo.getDescriptionChin());
                                psInsert.setString(6, vo.getAvailableSize());
                                psInsert.setString(7, vo.getAvailableSizeChin());
                                psInsert.setString(8, vo.getTileThickness());
                                psInsert.setString(9, vo.getTileThicknessChin());
                                psInsert.setString(10, vo.getColor());
                                psInsert.setString(11, vo.getColorChin());
                                psInsert.setString(12, vo.getFinishing());
                                psInsert.setString(13, vo.getFinishingChin());
                                psInsert.setString(14, vo.getApplication());
                                psInsert.setString(15, vo.getApplicationChin());
                                psInsert.setString(16, vo.getRemarks1());
                                psInsert.setString(17, vo.getRemarks1Chin());
                                psInsert.setString(18, vo.getRemarks2());
                                psInsert.setString(19, vo.getRemarks2Chin());
                                psInsert.setString(20, vo.getRemarks3());
                                psInsert.setString(21, vo.getRemarks3Chin());
                                psInsert.setString(22, vo.getTranAction().toUpperCase());
                                psInsert.setString(23, Util.TRAN_STATUS_AWV);
                                psInsert.setString(24, Util.USER_ID_DEFAULT);
                                psInsert.setString(25, Util.USER_ID_DEFAULT);
//                              psInsert.setInt(26,  MemCache.getUploadSeq());
                                psInsert.setInt(26,  refIdx);
                                psInsert.setInt(27, vo.getExcelRowID());
                                psInsert.setString(28, vo.getExcelRowData());
                                psInsert.executeUpdate();
                        }
                       
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                finally {
        			try {
        				if (psInsert != null) {psInsert.close();}
        				if (conn != null) {conn.close();}
        			} catch(SQLException e) {
        				e.printStackTrace();
        			}
        		}		
               
        }
}
