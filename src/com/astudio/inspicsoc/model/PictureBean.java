/**
 * 
 */
package com.astudio.inspicsoc.model;

/**
 * @author CJH 图片实体用于上传
 */
public class PictureBean {

	private String name;

	private String srcUrl;

	private Integer width;

	private Integer height;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSrcUrl() {
		return srcUrl;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

}
