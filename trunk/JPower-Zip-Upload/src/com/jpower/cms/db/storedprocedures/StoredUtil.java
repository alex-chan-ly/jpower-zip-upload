package com.jpower.cms.db.storedprocedures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StoredUtil {

	public static int isFileExist(String pathPrefix, String path,
			String fileName) {
		int rtnCode = 0;
		String fullPath = null;

		if (fileName == null || fileName.length() == 0 || path == null
				|| path.length() == 0) {
			rtnCode = -1;
		} else {
			if (pathPrefix != null && pathPrefix.length() > 0) {
				fullPath = pathPrefix + File.separator + path + File.separator
						+ fileName;
			} else {
				fullPath = path + File.separator + fileName;
			}
			File f = new File(fullPath);

			rtnCode = (f.isFile() && f.exists()) ? 0 : -1;
		}
		return rtnCode;
	}

	public static String purgeFile(String pathPrefix, String path, String fileName) {

		String rtnStr = "";
		String fullPath = null;

		if (fileName == null || fileName.length() == 0 || path == null
				|| path.length() == 0) {
			rtnStr = "";
		} else {
			if (pathPrefix != null && pathPrefix.length() > 0) {
				fullPath = pathPrefix + File.separator + path + File.separator
						+ fileName;
			} else {
				fullPath = path + File.separator + fileName;
			}
			File f = new File(fullPath);

			if (f.isFile() && f.exists()) {
				f.delete();
				rtnStr = fullPath;
			} else {
				rtnStr = "";
			}
		}
		return rtnStr;
	}

	public static String copyFile(String pathPrefix, String srcPath, String srFile, String dstPath, String dtFile) {
		String rtnStr = null;
		String srFullFilePath = null;
		String dtFullFilePath = null;
		String localPathPrefix = null;
		
		if (pathPrefix != null && pathPrefix.length() > 0) {
			localPathPrefix = pathPrefix + File.separator;
		} else {
			localPathPrefix = File.separator;
		}
		
		if (srcPath != null && srcPath.length() > 0) {
			srFullFilePath = localPathPrefix + srcPath + File.separator + srFile; 
		}
		
		if (dstPath != null && dstPath.length() > 0) {	
			dtFullFilePath = localPathPrefix + dstPath + File.separator + dtFile; 
		}
	
		File f1 = new File(srFullFilePath);
		File f2 = new File(dtFullFilePath);

		try {
			InputStream in = new FileInputStream(f1);
			OutputStream out = new FileOutputStream(f2);

			try {
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
				rtnStr = dtFullFilePath;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				rtnStr = "";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnStr = "";
		}
		return rtnStr;
	}
}
