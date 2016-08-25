package com.dom.chacheutil;


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "com.dom.dev/tmps/";
		} else {
			return CommonUtil.getRootFilePath() + "com.dom.dev/tmps";
		}
	}
}
