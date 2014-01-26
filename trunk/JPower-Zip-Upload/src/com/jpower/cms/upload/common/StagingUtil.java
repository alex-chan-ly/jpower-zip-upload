package com.jpower.cms.upload.common;

import java.io.File;
import java.io.IOException;

public class StagingUtil {

	public static void purgeStagingArea() {
		
	String stagingHome = FileHelper.getConfigProperty("staging.home");
	String stagingDir = FileHelper.getConfigProperty("staging.directory");
		
	try {
		if (stagingHome != null && stagingDir != null) {
			stagingDir = stagingHome + File.separator + stagingDir + File.separator + "*";
			String[] cmd = {"/bin/sh", "-c", "rm -fr " + stagingDir};
			Runtime.getRuntime().exec(cmd);

		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
}
