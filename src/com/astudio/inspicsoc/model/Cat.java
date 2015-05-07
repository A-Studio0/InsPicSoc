package com.astudio.inspicsoc.model;

public class Cat {
	private String name;
	private int imageId;
	public Cat (String name,int imageId){
		this.name = name;
		this.imageId = imageId;
	}
	public String getName(){
		return name;
	}
	public int getImageId(){
		return imageId;
	}
}
