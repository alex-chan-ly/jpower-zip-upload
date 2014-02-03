package com.jpower.cms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jpower.cms.upload.common.DBAccess;
import com.jpower.cms.vo.InventoryApplicationDetailVO;
import com.jpower.cms.vo.InventoryApplicationVO;

public class CommonDAO {

	public static String sql1 = "select lob.lob_id, lob.sub_lob_id, lob.sub_lob_label_eng, lob.sub_lob_label_chin, "
			+ "cat.category_id, rlt_lob_cat.lob_category_seq, cat.category_label_eng, cat.category_label_chin, cat.category_image, 'ADD' as ACTION, "
			+ "ser.series_id, rlt_cat_ser.cat_series_seq, ser.series_label_eng, ser.series_label_chin, ser.series_image_small, ser.series_image_large, "
			+ "sub_ser.sub_series_id, rlt_ser_sub_ser.series_sub_series_seq, sub_ser.material_id, sub_ser.sub_series_image_small, sub_ser.sub_series_image_large "
			+ "from jpt_lob lob, jpt_category cat, jpt_rlt_lob_category rlt_lob_cat, "
			+ "jpt_series ser, jpt_rlt_category_series rlt_cat_ser, jpt_sub_series sub_ser, jpt_rlt_series_sub_series rlt_ser_sub_ser "
			+ "where lob.lob_pk = rlt_lob_cat.lob_pk and rlt_lob_cat.category_pk = cat.category_pk and cat.category_pk = rlt_cat_ser.category_pk "
			+ "and rlt_cat_ser.series_pk = ser.series_pk and ser.series_pk = rlt_ser_sub_ser.series_pk and rlt_ser_sub_ser.sub_series_pk = sub_ser.sub_series_pk "
			+ "and sub_ser.rec_status = 'ACT' and rlt_ser_sub_ser.rec_status = 'ACT' and ser.rec_status = 'ACT' and rlt_cat_ser.rec_status = 'ACT' "
			+ "and cat.rec_status = 'ACT' and rlt_lob_cat.rec_status = 'ACT' and lob.rec_status = 'ACT' "
			+ "order by lob.lob_id, lob.sub_lob_id, rlt_lob_cat.lob_category_seq, rlt_cat_ser.cat_series_seq, rlt_ser_sub_ser.series_sub_series_seq";

	public static String sql2 = "select material_id, series, name, description_eng, description_chin, avaliable_size, avaliable_size_chin, tile_thickness, tile_thickness_chin, "
			+ "color, color_chin, finishing, finishing_chin, application, application_chin, remarks_1, remarks_1_chin, rec_status "
			+ "from jpt_material where rec_status = 'ACT' order by material_id";
	
	public static List<InventoryApplicationVO> extractInventoryApplication() {
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		List<InventoryApplicationVO> voList = new ArrayList();

		try {
			conn = DBAccess.getDBConnection();
			s = conn.createStatement();
			rs = s.executeQuery(sql1);

			while (rs.next()) {
				InventoryApplicationVO vo = new InventoryApplicationVO();
				vo.setLob(rs.getString(1));
				vo.setSubLob(rs.getString(2));
				vo.setSubLobLabelEng(rs.getString(3));
				vo.setSubLobLabelChin(rs.getString(4));
				vo.setCategory(rs.getString(5));
				vo.setCategorySeq(rs.getShort(6));
				vo.setCategoryLabelEng(rs.getString(7));
				vo.setCategoryLabelChin(rs.getString(8));
				vo.setCategoryImagePath(rs.getString(9));
				vo.setTranAction(rs.getString(10));
				vo.setSeries(rs.getString(11));
				vo.setSeriesSeq(rs.getShort(12));
				vo.setSeriesLabelEng(rs.getString(13));
				vo.setSeriesLabelChin(rs.getString(14));
				vo.setSeriesImageSmallPath(rs.getString(15));
				vo.setSeriesImageLargePath(rs.getString(16));
				vo.setSubSeries(rs.getString(17));
				vo.setSubSeriesSeq(rs.getShort(18));
				vo.setSubSeriesID(rs.getString(19));
				vo.setSubSeriesImageSmallPath(rs.getString(20));
				vo.setSubSeriesImageLargePath(rs.getString(21));
								
				voList.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return voList;
	}
	
	public static List<InventoryApplicationDetailVO> extractInventoryApplicationDetail() {
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		List<InventoryApplicationDetailVO> voList = new ArrayList();

		try {
			conn = DBAccess.getDBConnection();
			s = conn.createStatement();
			rs = s.executeQuery(sql2);

			while (rs.next()) {
				InventoryApplicationDetailVO vo = new InventoryApplicationDetailVO();
				vo.setSubSeriesId(rs.getString(1));
				vo.setSeries(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDescriptionEng(rs.getString(4));
				vo.setDescriptionChin(rs.getString(5));
				vo.setAvailableSize(rs.getString(6));
				vo.setAvailableSizeChin(rs.getString(7));
				vo.setTileThickness(rs.getString(8));
				vo.setTileThicknessChin(rs.getString(9));
				vo.setColor(rs.getString(10));
				vo.setColorChin(rs.getString(11));
				vo.setFinishing(rs.getString(12));
				vo.setFinishingChin(rs.getString(13));
				vo.setApplication(rs.getString(14));
				vo.setApplicationChin(rs.getString(15));
				vo.setRemarks1(rs.getString(16));
				vo.setRemarks1Chin(rs.getString(17));
				vo.setTranAction(rs.getString(18));
				
				voList.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return voList;
	}	
}
