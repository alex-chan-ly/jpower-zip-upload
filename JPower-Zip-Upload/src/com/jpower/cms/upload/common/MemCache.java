package com.jpower.cms.upload.common;

public class MemCache {
	private static int uploadSeq = 0;

	public static int getUploadSeq() {
		return uploadSeq;
	}

	public static void setUploadSeq(int uploadSeq) {
		MemCache.uploadSeq = uploadSeq;
	}
}
