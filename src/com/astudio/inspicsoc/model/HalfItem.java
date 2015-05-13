package com.astudio.inspicsoc.model;

import com.astudio.inspicsoc.R;

import android.graphics.Bitmap;

public class HalfItem {

	private String imageId;
	private String oneHeadId;
	private String twoHeadId;
	private String oneNameId;
	private String twoNameId;
	private int bottomId;
	
	public HalfItem(String imageId,String oneHeadId,String oneNameId, String twoHeadId, String twoNameId) {
		this.imageId = imageId;
		this.oneHeadId=oneHeadId;
		this.twoHeadId=twoHeadId;
		this.oneNameId=oneNameId;
		this.twoNameId=twoNameId;
		this.bottomId=R.drawable.photo_half_bottom;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getOneHeadId() {
		return oneHeadId;
	}

	public void setOneHeadId(String oneHeadId) {
		this.oneHeadId = oneHeadId;
	}

	public String getTwoHeadId() {
		return twoHeadId;
	}

	public void setTwoHeadId(String twoHeadId) {
		this.twoHeadId = twoHeadId;
	}

	public String getOneNameId() {
		return oneNameId;
	}

	public void setOneNameId(String oneNameId) {
		this.oneNameId = oneNameId;
	}

	public String getTwoNameId() {
		return twoNameId;
	}

	public void setTwoNameId(String twoNameId) {
		this.twoNameId = twoNameId;
	}

	public int getBottomId() {
		return bottomId;
	}

	public void setBottomId(int bottomId) {
		this.bottomId = bottomId;
	}

}
