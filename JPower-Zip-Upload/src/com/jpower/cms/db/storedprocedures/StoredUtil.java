package com.jpower.cms.db.storedprocedures;

import java.io.File;

public class StoredUtil {

	public static int isFileExist(String pathPrefix, String path, String fileName) {
		int rtnCode = 0;
		String fullPath = null;
		
		if (fileName == null || fileName.length() == 0 
				|| path == null || path.length() == 0) {
			rtnCode = -1;
		} else {
			if (pathPrefix != null && pathPrefix.length() > 0) {
				fullPath = pathPrefix + File.separator + path + File.separator + fileName;
			} else {
				fullPath = path + File.separator + fileName;
			}
			
			File f = new File(fullPath);
			
			rtnCode = (f.isFile() && f.exists()) ? 0 : -1;
		}
		return rtnCode;
	}
	

}
