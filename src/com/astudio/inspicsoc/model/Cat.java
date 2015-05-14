package com.astudio.inspicsoc.model;

public class Cat {
	private String name;
	private int imageId;
	private boolean isSelectOn;
	public Cat (String name,int imageId){
		this.name = name;
		this.imageId = imageId;
		this.isSelectOn = false;
	}
	public String getName(){
		return name;
	}
	public int getImageId(){
		return imageId;
	}
	public boolean isSelectOn(){
		return this.isSelectOn;
	}
	public void selectOff(){
		this.isSelectOn = false;
	}
}
