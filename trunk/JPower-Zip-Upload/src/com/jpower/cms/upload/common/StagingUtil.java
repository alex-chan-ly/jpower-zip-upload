package com.jpower.cms.upload.common;

import java.io.File;
import java.io.IOException;

public class StagingUtil {

	private static String stagingHome = FileHelper
			.getConfigProperty("staging.home");
	private static String stagingDir = FileHelper
			.getConfigProperty("staging.directory");

	public static void purgeStagingArea() {
		String fullPathFileName = null;

		try {
			if (stagingHome != null && stagingDir != null) {
				fullPathFileName = stagingHome + File.separator + stagingDir
						+ File.separator + "*";
				String[] cmd = { "/bin/sh", "-c", "rm -fr " + fullPathFileName };
				Runtime.getRuntime().exec(cmd);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int unZipInventoryFile(String zipfileName) {
		int rtnCode = 0;
		String fullPathFileName = null;

		if (stagingHome != null && stagingDir != null) {
			fullPathFileName = stagingHome + File.separator + stagingDir
					+ File.separator + zipfileName;
			System.out.println(fullPathFileName);

			File f = new File(fullPathFileName);
			if (f.isFile() && f.exists() && f.canRead()) {
				String[] cmd = {
						"/bin/sh",
						"-c",
						"unzip -j " + fullPathFileName + " -d " + stagingHome
								+ File.separator + stagingDir };
				try {
					Process p = Runtime.getRuntime().exec(cmd);
					int rtn = p.waitFor();
					if (rtn == 0) {
						rtnCode = 0;
					} else {
						rtnCode = -1;
					}

				} catch (IOException e) {
					e.printStackTrace();
					rtnCode = -1;
				} catch (InterruptedException e) {
					e.printStackTrace();
					rtnCode = -1;
				}
			}
		}
		return rtnCode;
	}
}
