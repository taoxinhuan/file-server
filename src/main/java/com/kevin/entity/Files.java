package com.kevin.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Files
 * @Description:文件信息
 * @author kevin
 */
public class Files implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	/**
	 * 文件原名
	 */
	private String filename;
	
	/**
	 * 文件名称
	 */
	private String nowname;
	
	/**
	 * 保存路径
	 */
	private String savepath;
	
	/**
	 * 访问历经
	 */
	private String url;
	
	/**
	 * 文件类型
	 */
	private String filetype;
	
	/**
	 * 文件大小（字节）
	 */
	private String filesize;
	
	/**
	 * 上传时间
	 */
	private Date uploadtime;
	
	public Files() {
	}
	
	public Files(String savepath) {
		this.savepath = savepath;
	}
	
	public Files(String fileName, String nowName, String savePath, String url, String fileType, String fileSize) {
		this.filename = fileName;
		this.nowname = nowName;
		this.savepath = savePath;
		this.url = url;
		this.filetype = fileType;
		this.filesize = fileSize;
		this.uploadtime = new Date();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename == null ? null : filename.trim();
	}
	
	public String getNowname() {
		return nowname;
	}
	
	public void setNowname(String nowname) {
		this.nowname = nowname == null ? null : nowname.trim();
	}
	
	public String getSavepath() {
		return savepath;
	}
	
	public void setSavepath(String savepath) {
		this.savepath = savepath == null ? null : savepath.trim();
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}
	
	public String getFiletype() {
		return filetype;
	}
	
	public void setFiletype(String filetype) {
		this.filetype = filetype == null ? null : filetype.trim();
	}
	
	public String getFilesize() {
		return filesize;
	}
	
	public void setFilesize(String filesize) {
		this.filesize = filesize == null ? null : filesize.trim();
	}
	
	public Date getUploadtime() {
		return uploadtime;
	}
	
	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}
	
	@Override
	public String toString() {
		return "[id=" + id + ", filename=" + filename + ", nowname=" + nowname + ", savepath=" + savepath + ", url="
				+ url + ", filetype=" + filetype + ", filesize=" + filesize + ", uploadtime=" + uploadtime + "]";
	}
}
