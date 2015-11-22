package com.pisual.witchatelier.model;

/**
 *yande picture Database model
 *用于防腐层转换之后的内部环境模型
 *@author felixerio
 *@version1.0 
 * **/
public class YandeCG {
	/**ID**/
	private Integer id;
	/**MD5**/
	private String  md5;
	/**标签**/
	private String  tags;
	/**文件地址**/
	private String  file_url;
	/**文件大小**/
	private String  file_size;
	/**本地文件地址**/
	private String  local_url;
	/**文件来源地址**/
	private String  source;
	/**是否下载完成**/
	private boolean downComplete;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getLocal_url() {
		return local_url;
	}
	public void setLocal_url(String local_url) {
		this.local_url = local_url;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public boolean isDownComplete() {
		return downComplete;
	}
	public void setDownComplete(boolean downComplete) {
		this.downComplete = downComplete;
	}
}