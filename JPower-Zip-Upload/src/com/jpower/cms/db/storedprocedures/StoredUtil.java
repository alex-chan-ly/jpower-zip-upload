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

	public static int copyFile(String srFile, String dtFile) {
		int rtnCode = 0;
		
		File f1 = new File(srFile);
		File f2 = new File(dtFile);

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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				rtnCode = -1;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnCode = -1;
		}
		return rtnCode;
	}
}
