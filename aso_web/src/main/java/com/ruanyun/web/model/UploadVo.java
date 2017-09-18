package com.ruanyun.web.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
/**
 * 文件上传结果类
 * @author LIUYANG
 *
 */
public class UploadVo {
	private int id;
	private String filename;
	private String fileType;
	private Long fileSize;


	/**
	 * 返回“0”：没有取到文件流，“-1”：没有配置上传要件，“-2”：文件类型不充许，“-3”：文件大小超过限制
	 */
	private int result;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getFileSize() {
		NumberFormat formatter = new DecimalFormat("#0.00");
		if (fileSize != null) {
			if (fileSize < 1<<20) {
				return formatter.format((fileSize*1.0) / (1<<10)) + " KB";
			}
			if (fileSize < 1<<30) {
				return formatter.format((fileSize*1.0) / (1<<20)) + " MB";
			}
		}
		return "0 KB";
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
}
