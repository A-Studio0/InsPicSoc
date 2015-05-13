/**
 * 
 */
package com.astudio.inspicsoc.model;

import java.io.File;

/**
 * @author CJH 上传Item
 */
public class UploadItem {

	private String path;
	private File file;

	/**
	 * 
	 */
	public UploadItem() {
		// TODO Auto-generated constructor stub
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
