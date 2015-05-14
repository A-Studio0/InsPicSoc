package com.astudio.inspicsoc.model;

public class PhotoDetailItem {
	private int userHead;
	private String userName;
	private int imageId;
	private String description;
	private String position;
	private String date;
	private String viewNum;
	private int comment;
	private String tag;
	
	public PhotoDetailItem (int userHead,String userName,int imageId,String description,String position,
			String date,String viewNum,int comment,String tag){
	
		this.userHead = userHead;
		this.userName = userName;
		this.description = description;
		this.imageId = imageId;
		this.position = position;
		this.date = date;
		this.viewNum = viewNum;
		this.comment = comment;
		this.tag = tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTag(){
		return tag;
	}
	public void setUserHead(int userHead) {
		this.userHead = userHead;
	}
	public int getUserHead(){
		return userHead;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName(){
		return userName;
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
