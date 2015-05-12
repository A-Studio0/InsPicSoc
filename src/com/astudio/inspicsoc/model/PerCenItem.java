package com.astudio.inspicsoc.model;

public class PerCenItem {
	private String description;
	private int imageId;
	private String position;
	private String date;
	private String collectNum;
	private String viewNum;
	private int comment;
	
	public PerCenItem (int imageId,String description,String position,
			String date,String collectNum,String viewNum,int comment){
	
		this.description = description;
		this.imageId = imageId;
		this.position = position;
		this.date = date;
		this.collectNum = collectNum;
		this.viewNum = viewNum;
		this.comment = comment;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription(){
		return description;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public int getImageId(){
		return imageId;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPosition(){
		return position;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate(){
		return date;
	}
	public void setCollectNum(String collectNum) {
		this.collectNum = collectNum;
	}
	public String getCollectNum(){
		return collectNum;
	}
	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}
	public String getViewNum(){
		return viewNum;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public int getComment(){
		return comment;
	}
	
}
